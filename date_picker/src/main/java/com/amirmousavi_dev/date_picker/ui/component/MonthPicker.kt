package com.amirmousavi_dev.date_picker.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.amirmousavi_dev.date_picker.R
import com.amirmousavi_dev.date_picker.core.JalaliCalendar
import com.amirmousavi_dev.date_picker.core.Month
import com.amirmousavi_dev.date_picker.model.DatePickerColors

@Composable
internal fun MonthPicker(
    displayedDate: JalaliCalendar,
    today: JalaliCalendar,
    colors: DatePickerColors,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier,
    onMonthSelected: (year: Int, month: Int) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(id = R.string.select_month),
            style = MaterialTheme.typography.titleMedium,
            color = colors.textColor,
            fontFamily = fontFamily
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(8.dp)
        ) {
            items(Month.entries.toTypedArray()) { month ->
                MonthButton(
                    month = month,
                    onMonthSelected = { onMonthSelected(displayedDate.year, month.monthNumber) },
                    isCurrentlySelected = displayedDate.month == month.monthNumber,
                    isCurrentDisplayMonth = today.month == month.monthNumber && today.year == displayedDate.year,
                    colors = colors,
                    fontFamily = fontFamily
                )
            }
        }
    }
}