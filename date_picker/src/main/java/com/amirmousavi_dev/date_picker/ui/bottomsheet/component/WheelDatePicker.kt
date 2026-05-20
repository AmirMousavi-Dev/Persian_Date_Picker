package com.amirmousavi_dev.date_picker.ui.bottomsheet.component

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amirmousavi_dev.date_picker.core.Month
import com.amirmousavi_dev.date_picker.core.PersianDate
import com.amirmousavi_dev.date_picker.util.FormatDigits
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
internal fun WheelDatePicker(
    initialDate: PersianDate,
    minDate: PersianDate? = null,
    maxDate: PersianDate? = null,
    fontFamily: FontFamily = FontFamily.Default,
    textColor: Color = Color.Black,
    onDateChanged: (PersianDate) -> Unit
) {
    var selectedYear by remember { mutableIntStateOf(initialDate.year) }
    var selectedMonth by remember { mutableIntStateOf(initialDate.month) }
    var preferredDay by remember { mutableIntStateOf(initialDate.day) }

    LaunchedEffect(initialDate) {
        selectedYear = initialDate.year
        selectedMonth = initialDate.month
        preferredDay = initialDate.day
    }

    val years = remember(minDate, maxDate) {
        val start = minDate?.year ?: 1300
        val end = maxDate?.year ?: 1500
        (start..end).toList()
    }
    val months = Month.entries

    // Function to get days in month correctly
    fun getDaysInMonth(year: Int, month: Int): Int {
        return when {
            month <= 6 -> 31
            month <= 11 -> 30
            else -> {
                // Check for leap year
                val isLeap = PersianDate(year, 1, 1).isLeapYear
                if (isLeap) 30 else 29
            }
        }
    }

    fun updateDate(year: Int, month: Int, day: Int, isManualDayChange: Boolean = false) {
        selectedYear = year
        selectedMonth = month
        if (isManualDayChange) {
            preferredDay = day
        }
        val daysInMonth = getDaysInMonth(year, month)
        val safeDay = if (isManualDayChange) day else preferredDay.coerceIn(1, daysInMonth)
        onDateChanged(PersianDate(year, month, safeDay))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Day Wheel
        Box(modifier = Modifier.weight(1f)) {
            val daysInMonth = remember(selectedYear, selectedMonth) {
                getDaysInMonth(selectedYear, selectedMonth)
            }
            val days = (1..daysInMonth).toList()
            val displayedDay = preferredDay.coerceIn(1, daysInMonth)

            InfiniteWheel(
                items = days,
                initialItem = displayedDay,
                fontFamily = fontFamily,
                textColor = textColor,
                onItemSelected = {
                    if (it != displayedDay) {
                        updateDate(selectedYear, selectedMonth, it, isManualDayChange = true)
                    }
                }
            )
        }

        // Month Wheel
        Box(modifier = Modifier.weight(1.5f)) {
            InfiniteWheel(
                items = months,
                initialItem = months.first { it.monthNumber == selectedMonth },
                label = { it.monthName.asString() },
                fontFamily = fontFamily,
                textColor = textColor,
                onItemSelected = {
                    if (it.monthNumber != selectedMonth) {
                        updateDate(selectedYear, it.monthNumber, preferredDay)
                    }
                }
            )
        }

        // Year Wheel
        Box(modifier = Modifier.weight(1.2f)) {
            InfiniteWheel(
                items = years,
                initialItem = selectedYear,
                fontFamily = fontFamily,
                textColor = textColor,
                onItemSelected = {
                    if (it != selectedYear) {
                        updateDate(it, selectedMonth, preferredDay)
                    }
                }
            )
        }
    }
}

@Composable
private fun <T> InfiniteWheel(
    items: List<T>,
    initialItem: T,
    label: @Composable (T) -> String = { it.toString() },
    fontFamily: FontFamily,
    textColor: Color,
    onItemSelected: (T) -> Unit
) {
    val itemHeight = 40.dp
    val visibleItemsCount = 5
    val infiniteFactor = 1000
    val totalItems = items.size * infiniteFactor
    val initialIndex = totalItems / 2 + items.indexOf(initialItem)

    val listState = rememberLazyListState(initialIndex - (visibleItemsCount / 2))
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val currentItems by rememberUpdatedState(items)
    val currentOnItemSelected by rememberUpdatedState(onItemSelected)

    val currentCenterIndex by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex + (visibleItemsCount / 2)
        }
    }

    // Sync scroll when initialItem changes externally (e.g. Go to Today)
    LaunchedEffect(initialItem) {
        val itemsList = currentItems
        val actualIndex = currentCenterIndex % itemsList.size
        if (itemsList[actualIndex] != initialItem) {
            val targetIndex = totalItems / 2 + itemsList.indexOf(initialItem)
            listState.animateScrollToItem(targetIndex - (visibleItemsCount / 2))
        }
    }

    // Adjust instantly when list size changes (e.g. 31 to 30 days) to prevent sensing the jump
    LaunchedEffect(items.size) {
        val itemsList = items
        val targetIndex = (itemsList.size * infiniteFactor) / 2 + itemsList.indexOf(initialItem)
        listState.scrollToItem(targetIndex - (visibleItemsCount / 2))
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { it + (visibleItemsCount / 2) }
            .distinctUntilChanged()
            .collectLatest { centerIndex ->
                val itemsList = currentItems
                if (itemsList.isEmpty()) return@collectLatest
                val actualIndex = centerIndex % itemsList.size
                currentOnItemSelected(itemsList[actualIndex])
            }
    }

    LazyColumn(
        state = listState,
        flingBehavior = flingBehavior,
        modifier = Modifier
            .height(itemHeight * visibleItemsCount)
            .fillMaxWidth()
    ) {
        items(totalItems) { index ->
            val actualIndex = index % items.size
            val item = items[actualIndex]
            val isSelected = index == currentCenterIndex

            Box(
                modifier = Modifier
                    .height(itemHeight)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val labelText = label(item)
                Text(
                    text = FormatDigits.toPersianDigits(labelText),
                    fontFamily = fontFamily,
                    fontSize = if (isSelected) 20.sp else 16.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = textColor,
                    modifier = Modifier.alpha(if (isSelected) 1f else 0.5f)
                )
            }
        }
    }
}
