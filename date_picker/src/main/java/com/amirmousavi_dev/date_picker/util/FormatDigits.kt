package com.amirmousavi_dev.date_picker.util

import kotlin.text.iterator

/**
 * A utility object for formatting digits in a string to Persian digits.
 */
object FormatDigits {

    private val persianDigits = arrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹') // More descriptive name

    /**
     * Converts digits in the given text to Persian digits.
     *
     * Replaces digits '0' to '9' with their corresponding Persian equivalents and
     * the decimal separator '٫' with '،'. Other characters remain unchanged.
     *
     * @param text The input string to convert.
     * @return The string with digits converted to Persian.
     */
    fun toPersianDigits(text: String): String {
        if (text.isEmpty()) {
            return ""
        }
        val persianDecimalSeparator = '،'

        var result = ""
        for (char in text) {
            result += when (char) {
                in '0'..'9' -> persianDigits[char - '0']
                '٫' -> persianDecimalSeparator
                else -> char
            }
        }
        return result
    }

}