package com.amirmousavi_dev.date_picker.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.Dialog
import com.amirmousavi_dev.date_picker.core.PersianDate
import com.amirmousavi_dev.date_picker.model.DatePickerColors
import com.amirmousavi_dev.date_picker.model.DatePickerDefaults
import com.amirmousavi_dev.date_picker.ui.component.PersianCalendarView

/**
 * Opens a Jalali DatePicker dialog.
 *
 * This composable provides a customizable Jalali DatePicker dialog, allowing users to select a date
 * in the Jalali calendar system.
 *
 * @param initialDate The initial date to be shown when the dialog opens. If null, the current date is used.
 * @param minDate The minimum selectable date.
 * @param maxDate The maximum selectable date.
 * @param onConfirm A callback that is invoked when the confirm button is clicked. The selected JalaliCalendar date is passed as a parameter.
 * @param onDismissRequest A callback that is invoked when the user requests to dismiss the dialog (e.g., by tapping outside or pressing the back button).
 * @param colors An object to customize the colors of the date picker dialog. See [com.amirmousavi_dev.date_picker.model.DatePickerDefaults.colors].
 * @param fontFamily The font family to be used for all text within the dialog.
 */

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

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Dialog(
            onDismissRequest = onDismissRequest,
        ) {


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

