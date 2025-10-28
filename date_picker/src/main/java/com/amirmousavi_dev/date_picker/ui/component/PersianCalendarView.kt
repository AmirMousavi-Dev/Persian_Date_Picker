package com.amirmousavi_dev.date_picker.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.amirmousavi_dev.date_picker.core.DatePickerType
import com.amirmousavi_dev.date_picker.core.JalaliCalendar
import com.amirmousavi_dev.date_picker.model.CalendarDimensions
import com.amirmousavi_dev.date_picker.model.DatePickerColors
import com.amirmousavi_dev.date_picker.ui.rememberDatePickerState

@Composable
internal fun PersianCalendarView(
    modifier: Modifier = Modifier,
    initialDate: JalaliCalendar?,
    minDate: JalaliCalendar? = null,
    maxDate: JalaliCalendar? = null,
    onSelectDay: (JalaliCalendar) -> Unit,
    onConfirm: (JalaliCalendar) -> Unit,
    onDismissRequest: () -> Unit,
    colors: DatePickerColors,
    fontFamily: FontFamily
) {
    val configuration = LocalConfiguration.current


    val state = rememberDatePickerState(initialDate = initialDate)


    val dimensions = remember {
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            CalendarDimensions(
                iconSize = 32.dp,
                weekDaysLabelPadding = 9.dp,
                yearSelectorHeight = 230.dp
            )
        } else {
            CalendarDimensions(
                iconSize = 43.dp,
                weekDaysLabelPadding = 18.dp,
                yearSelectorHeight = 280.dp
            )
        }
    }


    Column(
        modifier = modifier
            .background(color = colors.backgroundColor)
    ) {

        CalendarHeader(
            currentDisplayDate = state.selectedDate,
            colors = colors,
            fontFamily = fontFamily,
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.headerBackgroundColor)
        )

        CalendarNavigation(
            displayedDate = state.displayedDate,
            pickerType = state.pickerType,
            dimensions = dimensions,
            colors = colors,
            fontFamily = fontFamily,
            onMonthChange = {
                state.changeMonth(it)
            },
            onPickerTypeChange = {
                state.changePickerType(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp, top = 16.dp)
        )



        when (state.pickerType) {
            DatePickerType.Day -> {
                DayPicker(
                    displayedDate = state.displayedDate,
                    selectedDate = state.selectedDate,
                    today = state.today,
                    minDate = minDate,
                    maxDate = maxDate,
                    dimensions = dimensions,
                    colors = colors,
                    fontFamily = fontFamily,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensions.weekDaysLabelPadding),
                    onSelectDay = {
                        state.selectDay(it)
                    }
                )

            }

            DatePickerType.Month -> {

                MonthPicker(
                    displayedDate = state.displayedDate,
                    today = state.today,
                    colors = colors,
                    fontFamily = fontFamily,
                    modifier = Modifier.fillMaxWidth(),
                    onMonthSelected = { year, month ->
                        state.changeMonth(JalaliCalendar(year = year, month = month, day = 1))
                        state.changePickerType(DatePickerType.Day)
                    }
                )
            }

            DatePickerType.Year -> {
                YearPicker(
                    displayedDate = state.displayedDate,
                    colors = colors,
                    fontFamily = fontFamily,
                    dimensions = dimensions,
                    modifier = modifier,
                    onYearSelected = { selectedYear ->
                        state.changeYear(
                            JalaliCalendar(selectedYear, state.displayedDate.month, 1)
                        )

                        state.changePickerType(DatePickerType.Day)
                    }
                )
            }
        }


        if (state.pickerType == DatePickerType.Day) {

            ConfirmationButtons(
                selectedDate = state.selectedDate,
                today = state.today,
                jalali = state.displayedDate,
                colors = colors,
                fontFamily = fontFamily,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 8.dp),
                onConfirm = onConfirm,
                onDismissRequest = onDismissRequest,
                onGoToToday = {
                    state.goToToday()
                    state.selectedDate.let(onSelectDay)
                }
            )
        }
    }
}
