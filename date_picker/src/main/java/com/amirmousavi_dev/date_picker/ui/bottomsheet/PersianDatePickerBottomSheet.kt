package com.amirmousavi_dev.date_picker.ui.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amirmousavi_dev.date_picker.core.PersianDate
import com.amirmousavi_dev.date_picker.model.DatePickerColors
import com.amirmousavi_dev.date_picker.model.DatePickerDefaults
import com.amirmousavi_dev.date_picker.ui.PersianDatePickerState
import com.amirmousavi_dev.date_picker.ui.bottomsheet.component.WheelDatePicker
import com.amirmousavi_dev.date_picker.ui.component.ConfirmationButtons

/**
 * Opens a Jalali DatePicker in a BottomSheet with Wheel Selection.
 *
 * This composable provides a customizable Jalali DatePicker in a bottom sheet using three
 * endless scrollable wheels for Year, Month, and Day.
 *
 * @param state The state object to control and observe the bottom sheet.
 * @param initialDate The initial date to be shown when the bottom sheet opens. If null, the current date or state's selected date is used.
 * @param minDate The minimum selectable date.
 * @param maxDate The maximum selectable date.
 * @param onSelectDay A callback that is invoked when a date is changed in the wheels.
 * @param colors An object to customize the colors.
 * @param fontFamily The font family to be used.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersianDatePickerBottomSheet(
    state: PersianDatePickerState,
    initialDate: PersianDate? = null,
    minDate: PersianDate? = null,
    maxDate: PersianDate? = null,
    onSelectDay: (PersianDate) -> Unit = {},
    colors: DatePickerColors = DatePickerDefaults.colors(),
    fontFamily: FontFamily = FontFamily.Default
) {
    if (state.isVisible) {
        PersianDatePickerBottomSheet(
            initialDate = initialDate ?: state.selectedDate ?: PersianDate.now(),
            minDate = minDate,
            maxDate = maxDate,
            onSelectDay = onSelectDay,
            onConfirm = {
                state.selectedDate = it
                state.dismiss()
            },
            onDismissRequest = { state.dismiss() },
            colors = colors,
            fontFamily = fontFamily,
        )
    }
}

/**
 * Opens a Jalali DatePicker in a BottomSheet with Wheel Selection.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersianDatePickerBottomSheet(
    initialDate: PersianDate? = null,
    minDate: PersianDate? = null,
    maxDate: PersianDate? = null,
    onSelectDay: (PersianDate) -> Unit = {},
    onConfirm: (PersianDate) -> Unit,
    onDismissRequest: () -> Unit,
    colors: DatePickerColors = DatePickerDefaults.colors(),
    fontFamily: FontFamily = FontFamily.Default
) {
    val sheetState = rememberModalBottomSheetState()
    var currentSelectedDate by remember { mutableStateOf(initialDate ?: PersianDate.now()) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = colors.backgroundColor,
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.backgroundColor)
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "انتخاب تاریخ",
                    fontFamily = fontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.headerTextColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.headerBackgroundColor)
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                WheelDatePicker(
                    initialDate = currentSelectedDate,
                    minDate = minDate,
                    maxDate = maxDate,
                    fontFamily = fontFamily,
                    textColor = colors.textColor,
                    onDateChanged = {
                        currentSelectedDate = it
                        onSelectDay(it)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ConfirmationButtons(
                    selectedDate = currentSelectedDate,
                    today = PersianDate.now(),
                    jalali = currentSelectedDate,
                    colors = colors,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    fontFamily = fontFamily,
                    onConfirm = { onConfirm(currentSelectedDate) },
                    onDismissRequest = onDismissRequest,
                    onGoToToday = {
                        currentSelectedDate = PersianDate.now()
                    }
                )
            }
        }
    }
}
