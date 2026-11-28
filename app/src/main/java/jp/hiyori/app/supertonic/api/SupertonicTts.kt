package jp.hiyori.app.supertonic.api

import jp.hiyori.app.supertonic.download.ModelDownloader
import jp.hiyori.app.supertonic.internal.InferenceEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Supertonic 3 Android runtime. Models and female voice styles are downloaded
 * only when the user explicitly starts the in-app download.
 */
class SupertonicTts(private val config: SupertonicConfig) {
    private val downloader = ModelDownloader(config.huggingFaceBaseUrl, config.storageDir)
    private val engine = InferenceEngine(config)

    fun isDownloaded(): Boolean = downloader.isComplete(SupertonicConfig.MODEL_FILES)

    fun download(): Flow<DownloadState> = downloader.download(SupertonicConfig.MODEL_FILES)

    suspend fun generate(text: String, voice: SupertonicVoice): GenerateResult =
        withContext(Dispatchers.Default) {
            if (!isDownloaded()) {
                return@withContext GenerateResult.Error(
                    IllegalStateException("音声モデルがまだ準備されていません")
                )
            }
            runCatching {
                if (!engine.isLoaded) engine.load()
                val styleFile = File(config.storageDir, "${voice.id}.json")
                check(styleFile.isFile) { "選択した声のデータがありません" }
                val wav = engine.generate(
                    text = text,
                    lang = "ja",
                    voiceStyleJson = styleFile.readText(),
                    speed = config.defaultSpeed,
                    steps = config.inferenceSteps
                )
                check(wav.size > 44) { "音声を作成できませんでした" }
                GenerateResult.Success(wav, ((wav.size - 44L) * 1000L) / (44100L * 2L))
            }.getOrElse { GenerateResult.Error(it) }
        }

    fun close() {
        engine.close()
    }
}
