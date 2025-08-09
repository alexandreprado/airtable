package com.airtable.interview.airtableschedule.timeline.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airtable.interview.airtableschedule.timeline.EventsState
import com.airtable.interview.airtableschedule.timeline.domain.MonthHeaderFmt

@Composable
fun Plane(
    state: EventsState,
    listState: LazyListState,
    monthHeaderHeight: Dp,
    dayRowHeight: Dp,
    laneWidth: Dp,
    laneSpacing: Dp,
    eventVPad: Dp
) {
    val hScroll = rememberScrollState()

    LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
        state.sections.forEach { sec ->
            stickyHeader {
                Surface(color = MaterialTheme.colorScheme.surface, shadowElevation = 1.dp) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(monthHeaderHeight)
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            sec.ym.format(MonthHeaderFmt),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
            item {
                val monthHeight = dayRowHeight * sec.days.size
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(monthHeight)
                        .horizontalScroll(hScroll)
                ) {
                    val totalWidth = (laneWidth + laneSpacing) * state.laneCount - laneSpacing
                    Box(
                        Modifier
                            .width(totalWidth)
                            .fillMaxHeight()
                    ) {
                        Column(Modifier.matchParentSize()) {
                            repeat(sec.days.size) {
                                Spacer(Modifier.height(dayRowHeight))
                                Divider(
                                    thickness = Dp.Hairline,
                                    color = MaterialTheme.colorScheme.outlineVariant
                                )
                            }
                        }

                        val monthStart = sec.days.first()
                        state.placed
                            .filter { it.event.startDate <= sec.days.last() && it.event.endDate >= sec.days.first() }
                            .forEach { pe ->
                                val start = maxOf(pe.event.startDate, sec.days.first())
                                val end = minOf(pe.event.endDate, sec.days.last())
                                val topRows = (start.toEpochDay() - monthStart.toEpochDay()).toInt()
                                val rows = (end.toEpochDay() - start.toEpochDay()).toInt() + 1

                                val x = (laneWidth + laneSpacing) * pe.lane
                                val y = dayRowHeight * topRows + eventVPad
                                val h = (dayRowHeight * rows) - (eventVPad * 2)

                                EventBlock(
                                    name = pe.event.name,
                                    colorArgb = pe.event.colorArgb,
                                    modifier = Modifier
                                        .absoluteOffset(x = x, y = y)
                                        .width(laneWidth)
                                        .height(h)
                                )
                            }
                    }
                }
            }
        }
    }
}
