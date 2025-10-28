package com.amirmousavi_dev.date_picker.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.amirmousavi_dev.date_picker.core.Month
import com.amirmousavi_dev.date_picker.model.DatePickerColors

@Composable
internal fun MonthButton(
    month: Month,
    onMonthSelected: (Int) -> Unit,
    isCurrentlySelected: Boolean,
    isCurrentDisplayMonth: Boolean,
    colors: DatePickerColors,
    fontFamily: FontFamily
) {
    TextButton(
        onClick = {
            onMonthSelected(month.monthNumber)

        },
        modifier = Modifier
            .drawBehind {
                if (isCurrentlySelected) {
                    drawCircle(colors.selectedIconColor)
                }
            }
            .padding(4.dp)) {
        Text(
            text = month.monthName.asString(),
            color = when {
                isCurrentlySelected -> colors.selectedTextColor
                isCurrentDisplayMonth -> colors.textColorHighlight
                else -> colors.textColor
            },
            fontFamily = fontFamily,

            )
    }
}