package jp.hiyori.app.speech

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import com.google.mlkit.genai.common.DownloadStatus
import com.google.mlkit.genai.common.FeatureStatus
import com.google.mlkit.genai.texttospeech.SpeechSynthesis
import com.google.mlkit.genai.texttospeech.SpeechSynthesizer
import com.google.mlkit.genai.texttospeech.SpeechSynthesizerRequest
import com.google.mlkit.genai.texttospeech.SpeechSynthesizerResponse
import com.google.mlkit.genai.texttospeech.VoiceInfo
import com.google.mlkit.genai.texttospeech.speechSynthesizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Locale

/**
 * Pixel 8/9+ on-device generative speech. Audio is 24 kHz, mono, signed 16-bit PCM.
 * Model download is only started after the user presses the preparation button.
 */
class GoogleGenAiSpeech {
    private val synthesizer: SpeechSynthesizer = SpeechSynthesis.getClient(
        speechSynthesizerOptions(Locale.JAPAN) {
            setVoiceFallbackEnabled(false)
        }
    )

    suspend fun femaleVoices(): List<VoiceInfo> =
        synthesizer.getSupportedVoices(Locale.JAPAN)
            .filter { it.gender == VoiceInfo.Gender.FEMALE }
            .sortedBy { it.name }

    suspend fun status(voice: VoiceInfo): Int = synthesizer.checkStatus(voice)

    fun download(voice: VoiceInfo): Flow<DownloadStatus> = synthesizer.download(voice)

    suspend fun speak(text: String, voice: VoiceInfo) = withContext(Dispatchers.IO) {
        val request = SpeechSynthesizerRequest(
            input = SpeechSynthesizerRequest.Input.Text(text, voice),
            audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
        )
        val output = java.io.ByteArrayOutputStream()
        synthesizer.synthesize(request).collect { response ->
            when (response) {
                is SpeechSynthesizerResponse.AudioAvailable -> output.write(response.audio)
                is SpeechSynthesizerResponse.Error ->
                    throw IllegalStateException("Google音声の生成に失敗しました: ${response.errorCode}")
                is SpeechSynthesizerResponse.StatusUpdate -> Unit
            }
        }
        val pcm = output.toByteArray()
        check(pcm.isNotEmpty()) { "Google音声データが空でした" }
        playPcm(pcm)
    }

    private fun playPcm(pcm: ByteArray) {
        val sampleRate = 24000
        val minBuffer = AudioTrack.getMinBufferSize(
            sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT
        )
        val track = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setSampleRate(sampleRate)
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(maxOf(minBuffer, pcm.size))
            .setTransferMode(AudioTrack.MODE_STATIC)
            .build()
        try {
            val written = track.write(pcm, 0, pcm.size, AudioTrack.WRITE_BLOCKING)
            check(written == pcm.size) { "Google音声を再生キューに渡せませんでした" }
            track.play()
            val sampleCount = pcm.size / 2
            while (track.playbackHeadPosition < sampleCount &&
                track.playState == AudioTrack.PLAYSTATE_PLAYING
            ) {
                Thread.sleep(20)
            }
        } finally {
            runCatching { track.stop() }
            track.release()
        }
    }

    fun close() = synthesizer.close()
}
