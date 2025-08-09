package com.airtable.interview.airtableschedule.timeline.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airtable.interview.airtableschedule.timeline.domain.DayHeaderFmt
import com.airtable.interview.airtableschedule.timeline.domain.MonthHeaderFmt
import com.airtable.interview.airtableschedule.timeline.domain.MonthSection
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun Rail(
    sections: List<MonthSection>,
    listState: LazyListState,
    railWidth: Dp,
    monthHeaderHeight: Dp,
    dayRowHeight: Dp
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .width(railWidth)
            .fillMaxHeight()
    ) {
        sections.forEach { sec ->
            stickyHeader {
                RailMonthHeader(sec.ym, monthHeaderHeight)
            }
            items(sec.days) { day ->
                RailDayHeader(day, dayRowHeight)
            }
        }
    }
}

@Composable
private fun RailMonthHeader(ym: YearMonth, height: Dp) {
    Surface(color = MaterialTheme.colorScheme.surface, shadowElevation = 1.dp) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(height)
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(ym.format(MonthHeaderFmt), style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Composable
private fun RailDayHeader(date: LocalDate, height: Dp) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(height),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            date.format(DayHeaderFmt),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider(
            Modifier.align(Alignment.BottomStart),
            thickness = Dp.Hairline,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}
