package com.amirmousavi_dev.date_picker.ui

import com.amirmousavi_dev.date_picker.core.DatePickerType
import com.amirmousavi_dev.date_picker.core.PersianDate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Internal DatePickerState Tests")
class DatePickerStateTest {

    @Test
    @DisplayName("Verify initial state of internal picker state")
    fun `initial internal state is correct`() {
        val initial = PersianDate(1403, 1, 1)
        val state = DatePickerState(initial)
        
        assertEquals(initial, state.selectedDate)
        assertEquals(initial, state.displayedDate)
        assertEquals(DatePickerType.Day, state.pickerType)
    }

    @Test
    @DisplayName("Changing picker type updates state")
    fun `changePickerType updates pickerType`() {
        val state = DatePickerState()
        state.changePickerType(DatePickerType.Year)
        assertEquals(DatePickerType.Year, state.pickerType)
    }

    @Test
    @DisplayName("Selecting a day updates selectedDate")
    fun `selectDay updates selectedDate`() {
        val state = DatePickerState(PersianDate(1403, 1, 1))
        val newDay = PersianDate(1403, 1, 15)
        state.selectDay(newDay)
        assertEquals(newDay, state.selectedDate)
    }

    @Test
    @DisplayName("Changing month updates displayedDate")
    fun `changeMonth updates displayedDate`() {
        val state = DatePickerState(PersianDate(1403, 1, 1))
        val newMonth = PersianDate(1403, 2, 1)
        state.changeMonth(newMonth)
        assertEquals(newMonth, state.displayedDate)
    }

    @Test
    @DisplayName("goToToday updates state to current date")
    fun `goToToday updates both selected and displayed dates`() {
        val state = DatePickerState(PersianDate(1300, 1, 1))
        state.goToToday()
        
        val now = PersianDate.now()
        assertEquals(now, state.selectedDate)
        assertEquals(now.year, state.displayedDate.year)
        assertEquals(now.month, state.displayedDate.month)
        assertEquals(1, state.displayedDate.day) // Displayed date usually points to start of month
    }
}
