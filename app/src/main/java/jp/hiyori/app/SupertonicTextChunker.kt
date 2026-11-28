package jp.hiyori.app

/** Keeps generated speech chunks short enough for on-device synthesis. */
internal fun splitSupertonicSpeechText(text: String, maxLength: Int = 140): List<String> {
    val remaining = StringBuilder(text.trim())
    val result = mutableListOf<String>()
    while (remaining.isNotEmpty()) {
        if (remaining.length <= maxLength) {
            result += remaining.toString()
            break
        }
        var cut = remaining.lastIndexOfAny(charArrayOf('。', '！', '？', '!', '?', '、', '，', ',', ' '), maxLength)
        if (cut < maxLength / 3) cut = maxLength
        else cut += 1
        if (cut > 0 && cut < remaining.length &&
            Character.isHighSurrogate(remaining[cut - 1]) &&
            Character.isLowSurrogate(remaining[cut])
        ) cut -= 1
        result += remaining.substring(0, cut).trim()
        remaining.delete(0, cut)
        while (remaining.isNotEmpty() && remaining[0].isWhitespace()) remaining.deleteCharAt(0)
    }
    return result.filter { it.isNotBlank() }
}
