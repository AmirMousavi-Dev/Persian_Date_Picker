package com.amirmousavi_dev.date_picker.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amirmousavi_dev.date_picker.R
import com.amirmousavi_dev.date_picker.core.JalaliCalendar
import com.amirmousavi_dev.date_picker.model.CalendarDimensions
import com.amirmousavi_dev.date_picker.model.DatePickerColors
import com.amirmousavi_dev.date_picker.util.FormatDigits

@Composable
internal fun YearPicker(
    displayedDate: JalaliCalendar,
    colors: DatePickerColors,
    fontFamily: FontFamily,
    dimensions: CalendarDimensions,
    modifier: Modifier = Modifier,
    onYearSelected: (Int) -> Unit
) {
    var selectedYear: Int? by remember {
        mutableStateOf(null)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.onBackground,
            text = stringResource(id = R.string.select_year),
            fontFamily = fontFamily
        )

        val minYear = displayedDate.year - 100
        val maxYear = displayedDate.year + 100
        val yearsList = (minYear..maxYear).toList()

        val scrollState =
            rememberLazyListState(initialFirstVisibleItemIndex = displayedDate.year - minYear)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensions.yearSelectorHeight)
                .padding(horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = scrollState
        ) {
            items(yearsList) { year ->
                val isSelected = selectedYear == year
                val isCurrentYear = displayedDate.year == year

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isSelected) colors.selectedIconColor.copy(alpha = 0.3f) else Color.Transparent)
                        .clickable { selectedYear = year }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = FormatDigits.toPersianDigits(year.toString()),
                        fontSize = 30.sp,
                        color = when {
                            isSelected -> colors.selectedTextColor
                            isCurrentYear -> colors.textColorHighlight
                            else -> colors.textColor
                        },
                        fontFamily = fontFamily,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Button(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            enabled = selectedYear != null,
            onClick = {
                selectedYear?.let { onYearSelected(it) }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.approveButtonColor,
                contentColor = colors.approveButtonTextColor
            )
        ) {
            Text(text = stringResource(id = R.string.approve), fontFamily = fontFamily)
        }
    }
}