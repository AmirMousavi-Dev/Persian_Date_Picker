package com.amirmousavi_dev.date_picker.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.amirmousavi_dev.date_picker.core.DatePickerType
import com.amirmousavi_dev.date_picker.core.JalaliCalendar

internal class DatePickerState(
    initialDate: JalaliCalendar? = null
) {
    val today = JalaliCalendar.now()


    var displayedDate by mutableStateOf(initialDate ?: today)
        private set

    var selectedDate by mutableStateOf(initialDate ?: today)
        private set

    var pickerType by mutableStateOf<DatePickerType>(DatePickerType.Day)
        private set



    fun selectDay(day: JalaliCalendar) {
        selectedDate = day
    }

    fun changeMonth(newJalali: JalaliCalendar) {
        displayedDate = newJalali
    }

    fun changeYear(newJalali: JalaliCalendar) {
        displayedDate = newJalali
    }

    fun changePickerType(type: DatePickerType) {
        pickerType = type
    }

    fun goToToday() {
        val tempToday = JalaliCalendar.now()
        displayedDate = JalaliCalendar(tempToday.year, tempToday.month, 1)
        selectedDate = tempToday
    }
}

@Composable
internal fun rememberDatePickerState(
    initialDate: JalaliCalendar? = null
): DatePickerState = remember {
    DatePickerState(initialDate)
}