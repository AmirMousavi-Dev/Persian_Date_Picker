package com.amirmousavi_dev.date_picker.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amirmousavi_dev.date_picker.R
import com.amirmousavi_dev.date_picker.core.JalaliCalendar
import com.amirmousavi_dev.date_picker.model.DatePickerColors

@Composable
internal fun ConfirmationButtons(
    selectedDate: JalaliCalendar?,
    today: JalaliCalendar,
    jalali: JalaliCalendar,
    colors: DatePickerColors,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier,
    onConfirm: (JalaliCalendar) -> Unit,
    onDismissRequest: () -> Unit,
    onGoToToday: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {

            Button(
                modifier = Modifier.padding(start = 8.dp),
                enabled = selectedDate != null,
                onClick = {
                    onConfirm(selectedDate!!)
                    onDismissRequest()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.approveButtonColor,
                    contentColor = colors.approveButtonTextColor
                )
            ) {
                Text(text = stringResource(id = R.string.approve), fontFamily = fontFamily)
            }

            OutlinedButton(
                modifier = Modifier.padding(start = 8.dp),
                onClick = onDismissRequest,
                border = BorderStroke(1.dp, colors.cancelButtonColor),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colors.cancelButtonColor,
                )
            ) {
                Text(
                    text = stringResource(id = R.string.cpt_cancel),
                    fontFamily = fontFamily
                )
            }
        }

        val isSelectedToday = selectedDate == today
        val isViewingTodayMonth = jalali.year == today.year && jalali.month == today.month


        val todayButtonAlpha = if (!isSelectedToday || !isViewingTodayMonth) 1f else 0f

        TextButton(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .alpha(todayButtonAlpha),
            onClick = onGoToToday,
            enabled = todayButtonAlpha > 0f
        ) {
            Text(
                text = stringResource(id = R.string.today),
                color = colors.toDayButtonColor,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily
            )
        }
    }
}