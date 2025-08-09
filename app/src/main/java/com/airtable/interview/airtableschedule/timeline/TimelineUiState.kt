package com.airtable.interview.airtableschedule.timeline

import com.airtable.interview.airtableschedule.timeline.domain.CumHeights
import com.airtable.interview.airtableschedule.timeline.domain.MonthSection
import com.airtable.interview.airtableschedule.timeline.domain.PlacedEvent
import com.airtable.interview.airtableschedule.timeline.domain.TimelineBounds
import java.time.YearMonth

/**
 * UI state for the timeline screen.
 */
sealed interface TimelineUiState {
    data object Idle : TimelineUiState
    data object Loading : TimelineUiState
    data class Success(val state: EventsState) : TimelineUiState
    data class Error(val throwable: Throwable) : TimelineUiState
}

data class EventsState(
    val bounds: TimelineBounds,
    val sections: List<MonthSection>,
    val placed: List<PlacedEvent>,
    val laneCount: Int,
    val railHeightsPx: IntArray,
    val planeHeightsPx: IntArray,
    val railCum: CumHeights,
    val planeCum: CumHeights,
    val monthStartIndexRail: Map<YearMonth, Int>,
    val monthStartIndexPlane: Map<YearMonth, Int>
)
