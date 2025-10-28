package com.amirmousavi_dev.date_picker.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.amirmousavi_dev.date_picker.core.DatePickerType
import com.amirmousavi_dev.date_picker.core.JalaliCalendar
import com.amirmousavi_dev.date_picker.model.CalendarDimensions
import com.amirmousavi_dev.date_picker.model.DatePickerColors
import com.amirmousavi_dev.date_picker.util.FormatDigits

@Composable
internal fun CalendarNavigation(
    displayedDate: JalaliCalendar,
    pickerType: DatePickerType,
    dimensions: CalendarDimensions,
    colors: DatePickerColors,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier,
    onMonthChange: (JalaliCalendar) -> Unit,
    onPickerTypeChange: (DatePickerType) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        if (pickerType == DatePickerType.Day) {
            IconButton(
                onClick = {
                    val newDate = if (displayedDate.month != 12) {
                        JalaliCalendar(displayedDate.year, displayedDate.month + 1, 1)
                    } else {
                        JalaliCalendar(displayedDate.year + 1, 1, 1)
                    }
                    onMonthChange(newDate)
                },
                modifier = Modifier.size(dimensions.iconSize),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                    contentDescription = "",
                    tint = colors.arrowLeftButtonColor
                )
            }
        } else {
            Spacer(modifier = Modifier.width(dimensions.iconSize))
        }



        OutlinedButton(
            onClick = {
                onPickerTypeChange(if (pickerType != DatePickerType.Year) DatePickerType.Year else DatePickerType.Day)
            },
            border = BorderStroke(1.dp, colors.yearButtonColor),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colors.monthButtonColor
            )
        ) {
            Text(
                text = FormatDigits.toPersianDigits(displayedDate.year.toString()),
                fontFamily = fontFamily
            )
        }


        OutlinedButton(
            onClick = {
                onPickerTypeChange(if (pickerType != DatePickerType.Month) DatePickerType.Month else DatePickerType.Day)
            },
            border = BorderStroke(1.dp, colors.monthButtonColor),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colors.monthButtonColor
            )
        ) {
            Text(
                text = FormatDigits.toPersianDigits(displayedDate.monthString),
                fontFamily = fontFamily
            )
        }


        if (pickerType == DatePickerType.Day) {
            IconButton(
                onClick = {
                    val newDate = if (displayedDate.month != 1) {
                        JalaliCalendar(displayedDate.year, displayedDate.month - 1, 1)
                    } else {
                        JalaliCalendar(displayedDate.year - 1, 12, 1)
                    }
                    onMonthChange(newDate)
                },
                modifier = Modifier.size(dimensions.iconSize)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = "",
                    tint = colors.arrowRightButtonColor
                )
            }
        } else {
            Spacer(modifier = Modifier.width(dimensions.iconSize))
        }
    }
}