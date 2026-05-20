package com.amirmousavi_dev.date_picker.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@DisplayName("PersianDate Tests")
class PersianDateTest {

    @Nested
    @DisplayName("Leap Year Tests")
    inner class LeapYearTests {
        @ParameterizedTest(name = "{0} should have isLeapYear = {1}")
        @CsvSource(
            "1403, true",
            "1402, false",
            "1399, true",
            "1400, false"
        )
        fun `verify leap year calculation`(year: Int, expectedIsLeap: Boolean) {
            assertEquals(expectedIsLeap, PersianDate(year, 1, 1).isLeapYear)
        }
    }

    @Nested
    @DisplayName("Month Length Tests")
    inner class MonthLengthTests {
        @ParameterizedTest(name = "Month {1} in year {0} should have {2} days")
        @CsvSource(
            "1402, 1, 31",
            "1402, 6, 31",
            "1402, 7, 30",
            "1402, 11, 30",
            "1402, 12, 29",
            "1403, 12, 30"
        )
        fun `verify length of month`(year: Int, month: Int, expectedLength: Int) {
            assertEquals(expectedLength, PersianDate(year, month, 1).lengthOfMonth)
        }
    }

    @Test
    @DisplayName("formattedDate returns correct string")
    fun `formattedDate returns correct string`() {
        val date = PersianDate(1403, 7, 10)
        assertEquals("1403/07/10", date.formattedDate)
    }

    @Test
    @DisplayName("plusDays handles transitions correctly")
    fun `plusDays handles month transitions correctly`() {
        val lastDayOfFarvardin = PersianDate(1403, 1, 31)
        val firstDayOfOrdibehesht = lastDayOfFarvardin.plusDays(1)
        
        assertEquals(1403, firstDayOfOrdibehesht.year)
        assertEquals(2, firstDayOfOrdibehesht.month)
        assertEquals(1, firstDayOfOrdibehesht.day)
    }

    @Test
    fun `plusDays handles year transitions correctly`() {
        val lastDayOfYear = PersianDate(1402, 12, 29)
        val firstDayOfNewYear = lastDayOfYear.plusDays(1)
        
        assertEquals(1403, firstDayOfNewYear.year)
        assertEquals(1, firstDayOfNewYear.month)
        assertEquals(1, firstDayOfNewYear.day)
    }

    @Test
    @DisplayName("dayName returns correct Persian name")
    fun `dayName returns correct Persian name`() {
        // 1403/07/10 settled as Wednesday (چهارشنبه) in this implementation
        val date = PersianDate(1403, 7, 10)
        assertEquals("چهارشنبه", date.dayName)
        
        // 1403/01/01 is Thursday (پنجشنبه)
        assertEquals("پنجشنبه", PersianDate(1403, 1, 1).dayName)
    }

    @ParameterizedTest(name = "Gregorian {0}-{1}-{2} should be Persian {3}-{4}-{5}")
    @CsvSource(
        "2024, 3, 20, 1403, 1, 1",
        "2024, 10, 1, 1403, 7, 10",
        "2002, 2, 3, 1380, 11, 14",
        "2025, 3, 20, 1403, 12, 30"
    )
    fun `verify Gregorian to Persian conversion`(gYear: Int, gMonth: Int, gDay: Int, pYear: Int, pMonth: Int, pDay: Int) {
        val result = PersianDate.fromGregorian(gYear, gMonth, gDay)
        assertEquals(pYear, result.year)
        assertEquals(pMonth, result.month)
        assertEquals(pDay, result.day)
    }

    @Test
    fun `compareTo works correctly`() {
        val d1 = PersianDate(1403, 1, 1)
        val d2 = PersianDate(1403, 1, 2)
        val d3 = PersianDate(1403, 2, 1)
        val d4 = PersianDate(1404, 1, 1)
        
        assertTrue(d1 < d2)
        assertTrue(d2 < d3)
        assertTrue(d3 < d4)
        assertEquals(0, d1.compareTo(PersianDate(1403, 1, 1)))
    }

    @Test
    fun `plusDays with negative values works correctly`() {
        val date = PersianDate(1403, 1, 1)
        val previousDay = date.plusDays(-1)
        assertEquals(1402, previousDay.year)
        assertEquals(12, previousDay.month)
        assertEquals(29, previousDay.day)
    }
}
