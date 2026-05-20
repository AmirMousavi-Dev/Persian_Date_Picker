package com.amirmousavi_dev.date_picker.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@DisplayName("FormatDigits Utility Tests")
class FormatDigitsTest {

    @ParameterizedTest(name = "Convert \"{0}\" to \"{1}\"")
    @CsvSource(
        "'123', '۱۲۳'",
        "'0', '۰'",
        "'456.78', '۴۵۶.۷۸'",
        "'', ''",
        "'Persian 123', 'Persian ۱۲۳'"
    )
    fun `toPersianDigits converts English digits to Persian`(input: String, expected: String) {
        assertEquals(expected, FormatDigits.toPersianDigits(input))
    }

    @Test
    @DisplayName("Verify decimal separator conversion if applicable")
    fun `toPersianDigits converts special decimal separator`() {
        // The implementation converts '٫' to '،'
        assertEquals("۱۲۳،۴۵", FormatDigits.toPersianDigits("123٫45"))
    }
}
