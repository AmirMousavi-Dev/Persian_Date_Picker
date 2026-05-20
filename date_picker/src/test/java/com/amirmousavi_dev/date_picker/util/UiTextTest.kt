package com.amirmousavi_dev.date_picker.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("UiText Tests")
class UiTextTest {

    @Test
    @DisplayName("DynamicString returns literal text")
    fun `DynamicString asString returns correct text`() {
        val text = "Hello World"
        val dynamic = UiText.DynamicString(text)
        assertEquals(text, dynamic.text)
    }
}
