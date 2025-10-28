package com.amirmousavi_dev.date_picker.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class DatePickerColors(
    val backgroundColor: Color,
    val headerBackgroundColor: Color,
    val headerTextColor: Color,
    val textColor: Color,
    val selectedIconColor: Color,
    val selectedTextColor: Color,
    val textColorHighlight: Color,
    val dayOfWeekLabelColor: Color,
    val arrowLeftButtonColor: Color,
    val arrowRightButtonColor: Color,
    val monthButtonColor: Color,
    val approveButtonColor: Color,
    val approveButtonTextColor: Color,
    val cancelButtonColor: Color,
    val yearButtonColor: Color,
    val toDayButtonColor: Color
)