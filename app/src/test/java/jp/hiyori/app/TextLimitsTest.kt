package jp.hiyori.app

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TextLimitsTest {
    @Test
    fun keepsTextWithinLimitUnchanged() {
        val text = "こんにちは"
        val result = limitGeneratedResponse(text)
        assertEquals(text, result.first)
        assertFalse(result.second)
    }

    @Test
    fun truncatesLongTextAndAddsNotice() {
        val text = "あ".repeat(MAX_MESSAGE_CHARS + 100)
        val result = limitGeneratedResponse(text)
        assertTrue(result.second)
        assertEquals(MAX_MESSAGE_CHARS, result.first.length)
        assertTrue(result.first.endsWith(RESPONSE_TRUNCATION_NOTICE))
    }

    @Test
    fun doesNotSplitSurrogatePair() {
        val text = "A😀B"
        assertEquals("A", truncateAtUtf16Boundary(text, 2))
        assertEquals("A😀", truncateAtUtf16Boundary(text, 3))
    }

    @Test
    fun negativeLimitReturnsEmptyText() {
        assertEquals("", truncateAtUtf16Boundary("abc", -1))
    }
}
