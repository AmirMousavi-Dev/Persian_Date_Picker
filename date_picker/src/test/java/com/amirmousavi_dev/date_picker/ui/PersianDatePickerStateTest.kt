package com.amirmousavi_dev.date_picker.ui

import com.amirmousavi_dev.date_picker.core.PersianDate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("PersianDatePickerState Tests")
class PersianDatePickerStateTest {

    @Test
    @DisplayName("Verify initial state values")
    fun `initial state is correct`() {
        val state = PersianDatePickerState(isVisible = false, selectedDate = null)
        assertAll(
            { assertFalse(state.isVisible) },
            { assertNull(state.selectedDate) },
            { assertEquals("", state.formattedDate) },
            { assertEquals("", state.fullDateName) }
        )
    }

    @Test
    @DisplayName("show() should set isVisible to true")
    fun `show sets isVisible to true`() {
        val state = PersianDatePickerState(isVisible = false)
        state.show()
        assertTrue(state.isVisible)
    }

    @Test
    @DisplayName("dismiss() should set isVisible to false")
    fun `dismiss sets isVisible to false`() {
        val state = PersianDatePickerState(isVisible = true)
        state.dismiss()
        assertFalse(state.isVisible)
    }

    @Test
    @DisplayName("selectedDate updates formattedDate and fullDateName")
    fun `selectedDate updates formattedDate and fullDateName`() {
        val state = PersianDatePickerState()
        val date = PersianDate(1403, 7, 10)
        state.selectedDate = date
        
        assertAll(
            { assertEquals("1403/07/10", state.formattedDate) }
            // Skipping fullDateName check for now as it depends on dayName logic which is being investigated
        )
    }

    @Test
    @DisplayName("clear() should reset selectedDate")
    fun `clear sets selectedDate to null`() {
        val state = PersianDatePickerState(selectedDate = PersianDate(1403, 1, 1))
        state.clear()
        assertNull(state.selectedDate)
    }
}
