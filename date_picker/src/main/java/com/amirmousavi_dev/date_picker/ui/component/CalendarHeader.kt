package com.amirmousavi_dev.date_picker.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.amirmousavi_dev.date_picker.core.JalaliCalendar
import com.amirmousavi_dev.date_picker.model.DatePickerColors
import com.amirmousavi_dev.date_picker.util.FormatDigits

@Composable
internal fun CalendarHeader(
    currentDisplayDate: JalaliCalendar,
    colors: DatePickerColors,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = AbsoluteAlignment.Right,
        modifier = modifier
    ) {
        Text(
            text = FormatDigits.toPersianDigits(currentDisplayDate.year.toString()),
            fontFamily = fontFamily,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            color = colors.headerTextColor,
        )

        Text(
            text = currentDisplayDate.dayOfWeekDayMonthString,
            fontFamily = fontFamily,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            color = colors.headerTextColor
        )
        HorizontalDivider()
    }
}