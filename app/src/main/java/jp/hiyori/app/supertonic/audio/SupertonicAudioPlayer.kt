package jp.hiyori.app.supertonic.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/** Plays Supertonic's 44.1 kHz mono PCM WAV output through Android AudioTrack. */
class SupertonicAudioPlayer {
    @Volatile private var activeTrack: AudioTrack? = null

    suspend fun play(wav: ByteArray) = withContext(Dispatchers.IO) {
        require(wav.size > 44) { "音声データが空です" }
        val pcm = wav.copyOfRange(44, wav.size)
        val sampleCount = pcm.size / 2
        val minBuffer = AudioTrack.getMinBufferSize(
            44100, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT
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
                    .setSampleRate(44100)
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(maxOf(minBuffer, pcm.size))
            .setTransferMode(AudioTrack.MODE_STATIC)
            .build()
        activeTrack = track
        try {
            val written = track.write(pcm, 0, pcm.size, AudioTrack.WRITE_BLOCKING)
            check(written == pcm.size) { "音声データを再生キューに渡せませんでした" }
            track.play()
            while (track.playbackHeadPosition < sampleCount && track.playState == AudioTrack.PLAYSTATE_PLAYING) {
                delay(20)
            }
        } finally {
            if (activeTrack === track) activeTrack = null
            runCatching { track.stop() }
            track.release()
        }
    }

    fun stop() {
        val track = activeTrack
        if (track != null) {
            runCatching { track.pause() }
            runCatching { track.flush() }
            runCatching { track.stop() }
        }
    }
}
