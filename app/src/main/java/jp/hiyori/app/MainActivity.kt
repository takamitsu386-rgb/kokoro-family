package jp.hiyori.app

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HiyoriApp() }
    }
}

private data class ChatMessage(val id: Int, val text: String)

private const val PREFS_NAME = "hiyori_chat"
private const val MESSAGES_KEY = "messages"

private fun loadMessages(context: Context): List<ChatMessage> {
    val saved = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        .getString(MESSAGES_KEY, null) ?: return emptyList()
    return try {
        val json = JSONArray(saved)
        List(json.length()) { index ->
            val item = json.getJSONObject(index)
            ChatMessage(item.getInt("id"), item.getString("text"))
        }
    } catch (_: Exception) {
        emptyList()
    }
}

private fun saveMessages(context: Context, messages: List<ChatMessage>) {
    val json = JSONArray()
    messages.forEach { message ->
        json.put(JSONObject().put("id", message.id).put("text", message.text))
    }
    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        .edit().putString(MESSAGES_KEY, json.toString()).apply()
}

private fun createBackup(messages: List<ChatMessage>): String {
    val items = JSONArray()
    messages.forEach { message ->
        items.put(JSONObject().put("id", message.id).put("text", message.text))
    }
    return JSONObject()
        .put("format", "hiyori-chat-backup")
        .put("version", 1)
        .put("messages", items)
        .toString(2)
}

private fun readBackup(context: Context, uri: Uri): List<ChatMessage> {
    val jsonText = context.contentResolver.openInputStream(uri)
        ?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }
        ?: throw IllegalArgumentException("ファイルを読み込めませんでした")
    val root = JSONObject(jsonText)
    require(root.optString("format") == "hiyori-chat-backup") {
        "ひよりちゃんのバックアップファイルではありません"
    }
    val items = root.getJSONArray("messages")
    return List(items.length()) { index ->
        val item = items.getJSONObject(index)
        ChatMessage(item.getInt("id"), item.getString("text"))
    }
}

@Composable
private fun HiyoriApp() {
    val context = LocalContext.current
    val messages = remember(context) {
        mutableStateListOf<ChatMessage>().apply { addAll(loadMessages(context)) }
    }
    var draft by remember { mutableStateOf("") }
    var nextId by remember { mutableStateOf((messages.maxOfOrNull { it.id } ?: -1) + 1) }
    var status by remember { mutableStateOf("") }
    var showClearDialog by remember { mutableStateOf(false) }

    val exportLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        if (uri != null) {
            try {
                context.contentResolver.openOutputStream(uri)?.bufferedWriter(Charsets.UTF_8)
                    ?.use { it.write(createBackup(messages)) }
                    ?: throw IllegalArgumentException("保存先を開けませんでした")
                status = "バックアップを保存しました"
            } catch (error: Exception) {
                status = "保存できませんでした: ${error.localizedMessage ?: "不明なエラー"}"
            }
        }
    }

    val importLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            try {
                val restored = readBackup(context, uri)
                messages.clear()
                messages.addAll(restored)
                saveMessages(context, messages)
                nextId = (messages.maxOfOrNull { it.id } ?: -1) + 1
                status = "${messages.size}件のメッセージを復元しました"
            } catch (error: Exception) {
                status = "読み込めませんでした: ${error.localizedMessage ?: "形式を確認してください"}"
            }
        }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("履歴を削除しますか？") },
            text = { Text("この端末に保存された会話をすべて削除します。バックアップがない場合、元に戻せません。") },
            confirmButton = {
                TextButton(onClick = {
                    messages.clear()
                    saveMessages(context, messages)
                    nextId = 0
                    showClearDialog = false
                    status = "会話履歴を削除しました"
                }) { Text("削除") }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) { Text("キャンセル") }
            }
        )
    }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text(
                    text = "ひよりちゃん",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "テキストチャット",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(onClick = {
                        exportLauncher.launch("hiyori-backup.json")
                    }, modifier = Modifier.weight(1f)) { Text("バックアップ") }
                    OutlinedButton(onClick = {
                        importLauncher.launch(arrayOf("application/json", "text/json", "*/*"))
                    }, modifier = Modifier.weight(1f)) { Text("復元") }
                    OutlinedButton(
                        onClick = { showClearDialog = true },
                        enabled = messages.isNotEmpty(),
                        modifier = Modifier.weight(1f)
                    ) { Text("履歴削除") }
                }

                if (messages.isEmpty()) {
                    Column(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("ここから会話を始められます")
                        Text(
                            "メッセージを入力して送信してください",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(messages, key = { it.id }) { message ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = message.text,
                                    modifier = Modifier.background(
                                        MaterialTheme.colorScheme.primaryContainer,
                                        RoundedCornerShape(16.dp)
                                    ).padding(horizontal = 14.dp, vertical = 10.dp),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = draft,
                        onValueChange = { draft = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("メッセージを入力") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val message = draft.trim()
                            if (message.isNotEmpty()) {
                                messages.add(ChatMessage(nextId++, message))
                                saveMessages(context, messages)
                                draft = ""
                            }
                        },
                        enabled = draft.isNotBlank()
                    ) { Text("送信") }
                }

                Text(
                    text = status.ifBlank {
                        "履歴は端末内に保存されます。AIの返答・音声機能は未実装です。"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
