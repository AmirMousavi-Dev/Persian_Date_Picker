package com.amirmousavi_dev.date_picker.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.Dialog
import com.amirmousavi_dev.date_picker.core.PersianDate
import com.amirmousavi_dev.date_picker.model.DatePickerColors
import com.amirmousavi_dev.date_picker.model.DatePickerDefaults
import com.amirmousavi_dev.date_picker.ui.PersianDatePickerState
import com.amirmousavi_dev.date_picker.ui.component.PersianCalendarView

/**
 * Opens a Jalali DatePicker dialog.
 *
 * This composable provides a customizable Jalali DatePicker dialog, allowing users to select a date
 * in the Jalali calendar system.
 *
 * @param state The state object to control and observe the dialog.
 * @param initialDate The initial date to be shown when the dialog opens. If null, the current date is used.
 * @param minDate The minimum selectable date.
 * @param maxDate The maximum selectable date.
 * @param onSelectDay A callback that is invoked when a day is selected.
 * @param colors An object to customize the colors of the date picker dialog. See [com.amirmousavi_dev.date_picker.model.DatePickerDefaults.colors].
 * @param fontFamily The font family to be used for all text within the dialog.
 */
@Composable
fun PersianDatePickerDialog(
    state: PersianDatePickerState,
    initialDate: PersianDate? = null,
    minDate: PersianDate? = null,
    maxDate: PersianDate? = null,
    onSelectDay: (PersianDate) -> Unit = {},
    colors: DatePickerColors = DatePickerDefaults.colors(),
    fontFamily: FontFamily = FontFamily.Default
) {
    if (state.isVisible) {
        PersianDatePickerDialog(
            initialDate = initialDate ?: state.selectedDate,
            minDate = minDate,
            maxDate = maxDate,
            onSelectDay = onSelectDay,
            onConfirm = {
                state.selectedDate = it
                state.dismiss()
            },
            onDismissRequest = { state.dismiss() },
            colors = colors,
            fontFamily = fontFamily
        )
    }
}

@Composable
fun PersianDatePickerDialog(
    initialDate: PersianDate? = null,
    minDate: PersianDate? = null,
    maxDate: PersianDate? = null,
    onSelectDay: (PersianDate) -> Unit = {},
    onConfirm: (PersianDate) -> Unit,
    onDismissRequest: () -> Unit,
    colors: DatePickerColors = DatePickerDefaults.colors(),
    fontFamily: FontFamily = FontFamily.Default
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            PersianCalendarView(
                initialDate = initialDate,
                minDate = minDate,
                maxDate = maxDate,
                onSelectDay = onSelectDay,
                onConfirm = onConfirm,
                onDismissRequest = onDismissRequest,
                colors = colors,
                fontFamily = fontFamily,
            )
        }
    }
}
