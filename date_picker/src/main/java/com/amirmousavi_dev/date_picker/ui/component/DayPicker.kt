package com.amirmousavi_dev.date_picker.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.amirmousavi_dev.date_picker.core.JalaliCalendar
import com.amirmousavi_dev.date_picker.model.CalendarDimensions
import com.amirmousavi_dev.date_picker.model.DatePickerColors
import com.amirmousavi_dev.date_picker.util.FormatDigits

@Composable
internal fun DayPicker(
    displayedDate: JalaliCalendar,
    selectedDate: JalaliCalendar?,
    today: JalaliCalendar,
    minDate: JalaliCalendar?,
    maxDate: JalaliCalendar?,
    dimensions: CalendarDimensions,
    colors: DatePickerColors,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier,
    onSelectDay: (JalaliCalendar) -> Unit
) {

    val weekDays = listOf("ش", "ی", "د", "س", "چ", "پ", "ج")
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        weekDays.forEach { dayName ->
            Text(text = dayName, color = colors.dayOfWeekLabelColor, fontFamily = fontFamily)
        }
    }


    val monthLength = displayedDate.monthLength
    val firstDayOfMonth = JalaliCalendar(displayedDate.year, displayedDate.month, 1)


    val emptyCellsBefore = (firstDayOfMonth.dayOfWeek - 1 + 7) % 7
    val totalCells = emptyCellsBefore + monthLength

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        contentPadding = PaddingValues(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(totalCells) { index ->
            val dayOfMonth = index - emptyCellsBefore + 1

            if (dayOfMonth >= 1 && dayOfMonth <= monthLength) {

                val day = dayOfMonth
                val currentDayDate = JalaliCalendar(displayedDate.year, displayedDate.month, day)
                val currentGregorianDate = currentDayDate.toGregorian()


                val isEnabled = remember(currentGregorianDate, minDate, maxDate) {
                    val isAfterMin = minDate?.let { currentGregorianDate >= it.toGregorian() } ?: true
                    val isBeforeMax = maxDate?.let { currentGregorianDate <= it.toGregorian() } ?: true
                    isAfterMin && isBeforeMax
                }

                val isSelected = selectedDate != null && day == selectedDate.day && displayedDate.year == selectedDate.year && displayedDate.month == selectedDate.month
                val isToday = day == today.day && displayedDate.year == today.year && displayedDate.month == today.month

                FilledIconButton(
                    onClick = { onSelectDay(currentDayDate) },
                    Modifier.size(dimensions.iconSize),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = if (isSelected) colors.selectedIconColor else Color.Transparent,
                        disabledContentColor = Color.Transparent
                    ),
                    enabled = isEnabled
                ) {
                    Text(
                        text = FormatDigits.toPersianDigits(day.toString()),
                        color = when {
                            !isEnabled -> Color.LightGray
                            isSelected -> colors.selectedTextColor
                            isToday -> colors.textColorHighlight
                            else -> colors.textColor
                        },
                        fontFamily = fontFamily
                    )
                }

            } else {

                Spacer(modifier = Modifier.size(dimensions.iconSize))
            }
        }
    }
}