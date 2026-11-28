package jp.hiyori.app

internal const val MAX_MESSAGE_CHARS = 20000
internal const val RESPONSE_TRUNCATION_NOTICE = "\n\n（長文のため、文字数上限に合わせて省略しました）"

// UTF-16のサロゲートペアを途中で分断せず、指定文字数以内に切り詰める。
internal fun truncateAtUtf16Boundary(text: String, maxChars: Int): String {
    if (text.length <= maxChars) return text
    var end = maxChars.coerceAtLeast(0)
    if (end > 0 && end < text.length &&
        Character.isHighSurrogate(text[end - 1]) && Character.isLowSurrogate(text[end])) {
        end--
    }
    return text.substring(0, end)
}

internal fun limitGeneratedResponse(text: String): Pair<String, Boolean> {
    if (text.length <= MAX_MESSAGE_CHARS) return text to false
    val contentLimit = MAX_MESSAGE_CHARS - RESPONSE_TRUNCATION_NOTICE.length
    return truncateAtUtf16Boundary(text, contentLimit).trimEnd() +
        RESPONSE_TRUNCATION_NOTICE to true
}

internal const val MAX_STORED_HISTORY_CHARS = 5_000_000

internal fun isStoredHistoryWithinLimit(serializedLength: Int): Boolean =
    serializedLength in 0..MAX_STORED_HISTORY_CHARS
