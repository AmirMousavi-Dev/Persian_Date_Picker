package com.amirmousavi_dev.date_picker.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object DatePickerDefaults {
    @Composable
    fun colors(
        backgroundColor: Color = MaterialTheme.colorScheme.background,
        headerBackgroundColor: Color = MaterialTheme.colorScheme.primary,
        headerTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        textColor: Color = MaterialTheme.colorScheme.onBackground,
        selectedIconColor: Color = MaterialTheme.colorScheme.primaryContainer,
        selectedTextColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        textColorHighlight: Color = MaterialTheme.colorScheme.primary,
        dayOfWeekLabelColor: Color = MaterialTheme.colorScheme.onBackground,
        arrowLeftButtonColor: Color = MaterialTheme.colorScheme.primary,
        arrowRightButtonColor: Color = MaterialTheme.colorScheme.primary,
        monthButtonColor: Color = MaterialTheme.colorScheme.primary,
        approveButtonColor: Color = MaterialTheme.colorScheme.primary,
        approveButtonTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        cancelButtonColor: Color = MaterialTheme.colorScheme.primary,
        yearButtonColor: Color = MaterialTheme.colorScheme.primary,
        toDayButtonColor: Color = MaterialTheme.colorScheme.primary
    ): DatePickerColors = DatePickerColors(
        backgroundColor = backgroundColor,
        headerBackgroundColor = headerBackgroundColor,
        headerTextColor = headerTextColor,
        textColor = textColor,
        selectedIconColor = selectedIconColor,
        selectedTextColor = selectedTextColor,
        textColorHighlight = textColorHighlight,
        dayOfWeekLabelColor = dayOfWeekLabelColor,
        arrowLeftButtonColor = arrowLeftButtonColor,
        arrowRightButtonColor = arrowRightButtonColor,
        monthButtonColor = monthButtonColor,
        approveButtonColor = approveButtonColor,
        approveButtonTextColor = approveButtonTextColor,
        cancelButtonColor = cancelButtonColor,
        yearButtonColor = yearButtonColor,
        toDayButtonColor = toDayButtonColor
    )
}