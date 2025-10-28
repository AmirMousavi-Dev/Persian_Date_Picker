package com.amirmousavi_dev.date_picker.core

/**
 * Represents the different components of a date picker.
 */
sealed class DatePickerType{
    object Year: DatePickerType()
    object Month: DatePickerType()
    object Day: DatePickerType()
}

