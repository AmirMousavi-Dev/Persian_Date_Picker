package com.amirmousavi_dev.date_picker.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.amirmousavi_dev.date_picker.core.PersianDate
import com.amirmousavi_dev.date_picker.ui.dialog.PersianDatePickerDialog

/**
 * A state object that can be hoisted to control and observe the [PersianDatePickerDialog].
 */
class PersianDatePickerState(
    isVisible: Boolean = false,
    selectedDate: PersianDate? = null
) {
    /**
     * Whether the dialog is currently visible.
     */
    var isVisible by mutableStateOf(isVisible)

    /**
     * The currently selected date.
     */
    var selectedDate by mutableStateOf(selectedDate)

    /**
     * The formatted string of the selected date, or an empty string if none is selected.
     */
    val formattedDate: String
        get() = selectedDate?.formattedDate ?: ""

    /**
     * The user-friendly Persian string of the selected date, or an empty string if none is selected.
     */
    val fullDateName: String
        get() = selectedDate?.fullDateName ?: ""

    /**
     * Shows the date picker dialog.
     */
    fun show() {
        isVisible = true
    }

    /**
     * Hides the date picker dialog.
     */
    fun dismiss() {
        isVisible = false
    }

    /**
     * Clears the selected date.
     */
    fun clear() {
        selectedDate = null
    }

    companion object {
        /**
         * The default [Saver] implementation for [PersianDatePickerState].
         */
        val Saver: Saver<PersianDatePickerState, *> = Saver(
            save = { listOf(it.isVisible, it.selectedDate?.year, it.selectedDate?.month, it.selectedDate?.day) },
            restore = { value ->
                val list = value as List<*>
                PersianDatePickerState(
                    isVisible = list[0] as Boolean,
                    selectedDate = if (list[1] != null) PersianDate(list[1] as Int, list[2] as Int, list[3] as Int) else null
                )
            }
        )
    }
}

/**
 * Creates and [remember]s a [PersianDatePickerState].
 */
@Composable
fun rememberPersianDatePickerState(
    initialVisibility: Boolean = false,
    initialDate: PersianDate? = null
): PersianDatePickerState = rememberSaveable(saver = PersianDatePickerState.Saver) {
    PersianDatePickerState(initialVisibility, initialDate)
}
