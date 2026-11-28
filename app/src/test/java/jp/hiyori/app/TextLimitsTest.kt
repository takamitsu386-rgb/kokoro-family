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

    @Test
    fun keepsTextAtExactLimitUnchanged() {
        val text = "あ".repeat(MAX_MESSAGE_CHARS)
        val result = limitGeneratedResponse(text)
        assertEquals(text, result.first)
        assertFalse(result.second)
    }

    @Test
    fun zeroLimitReturnsEmptyText() {
        assertEquals("", truncateAtUtf16Boundary("abc", 0))
    }

    @Test
    fun generatedResponseTruncationDoesNotLeaveHalfSurrogate() {
        val contentLimit = MAX_MESSAGE_CHARS - RESPONSE_TRUNCATION_NOTICE.length
        val text = "あ".repeat(contentLimit - 1) + "😀" + "B".repeat(100)
        val result = limitGeneratedResponse(text)

        assertTrue(result.second)
        assertTrue(result.first.endsWith(RESPONSE_TRUNCATION_NOTICE))
        assertTrue(result.first.length <= MAX_MESSAGE_CHARS)
        assertFalse(result.first.dropLast(RESPONSE_TRUNCATION_NOTICE.length).last().isHighSurrogate())
    }
    @Test
    fun doesNotProduceUnpairedSurrogatesAtAnyBoundary() {
        val text = "A😀B😺C🚀D"

        for (limit in 0..text.length + 1) {
            val result = truncateAtUtf16Boundary(text, limit)
            assertTrue("length must not exceed limit=$limit", result.length <= limit)
            var index = 0
            while (index < result.length) {
                val char = result[index]
                if (Character.isHighSurrogate(char)) {
                    assertTrue("high surrogate must have a following low surrogate at limit=$limit",
                        index + 1 < result.length && Character.isLowSurrogate(result[index + 1]))
                    index += 2
                } else {
                    assertFalse("low surrogate must not appear without a high surrogate at limit=$limit",
                        Character.isLowSurrogate(char))
                    index++
                }
            }
        }
    }
}
