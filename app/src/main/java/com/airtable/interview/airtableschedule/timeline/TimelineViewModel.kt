package com.airtable.interview.airtableschedule.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airtable.interview.airtableschedule.models.Event
import com.airtable.interview.airtableschedule.repositories.EventDataRepository
import com.airtable.interview.airtableschedule.repositories.EventDataRepositoryImpl
import com.airtable.interview.airtableschedule.timeline.domain.TimeLineEvent
import com.airtable.interview.airtableschedule.timeline.domain.assignLanes
import com.airtable.interview.airtableschedule.timeline.domain.buildCumHeights
import com.airtable.interview.airtableschedule.timeline.domain.buildMonthSections
import com.airtable.interview.airtableschedule.timeline.domain.calculateEventColor
import com.airtable.interview.airtableschedule.timeline.domain.computeBounds
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.time.YearMonth

/**
 * ViewModel responsible for managing the state of the timeline screen.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TimelineViewModel : ViewModel() {
    private val eventDataRepository: EventDataRepository = EventDataRepositoryImpl()

    val uiState: StateFlow<TimelineUiState> = eventDataRepository
        .getTimelineItems()
        .mapLatest(::buildUiState)
        .catch { TimelineUiState.Error(it) }
        .onStart { TimelineUiState.Loading }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = TimelineUiState.Idle,
        )

    private fun buildUiState(events: List<Event>): TimelineUiState {
        val uiEvents = events.toTimelineEvents()
        val bounds = computeBounds(uiEvents)
        val sections = buildMonthSections(bounds)
        val placed = assignLanes(uiEvents)
        val laneCount = placed.maxOfOrNull { it.lane }?.plus(1) ?: 0

        val railHeights = buildList {
            sections.forEach { sec ->
                add(monthHeaderHeightPx)
                repeat(sec.days.size) { add(dayRowHeightPx) }
            }
        }.toIntArray()

        val railCum = buildCumHeights(railHeights)

        val planeHeights = buildList {
            sections.forEach { sec ->
                add(monthHeaderHeightPx)
                add(sec.days.size * dayRowHeightPx)
            }
        }.toIntArray()
        val planeCum = buildCumHeights(planeHeights)

        val monthStartIndexRail = mutableMapOf<YearMonth, Int>()
        val monthStartIndexPlane = mutableMapOf<YearMonth, Int>()
        run {
            var iRail = 0
            var iPlane = 0
            sections.forEach { sec ->
                monthStartIndexRail[sec.ym] = iRail
                monthStartIndexPlane[sec.ym] = iPlane
                iRail += 1 + sec.days.size
                iPlane += 2
            }
        }

        val eventsState = EventsState(
            bounds = bounds,
            sections = sections,
            placed = placed,
            laneCount = laneCount,
            railHeightsPx = railHeights,
            planeHeightsPx = planeHeights,
            railCum = railCum,
            planeCum = planeCum,
            monthStartIndexRail = monthStartIndexRail,
            monthStartIndexPlane = monthStartIndexPlane
        )

        return TimelineUiState.Success(eventsState)
    }

    val monthHeaderHeightPx: Int = 40
    val dayRowHeightPx: Int = 36
}

fun List<Event>.toTimelineEvents(): List<TimeLineEvent> =
    this.map { event ->
        TimeLineEvent(
            id = event.id,
            startDate = event.startDate,
            endDate = event.endDate,
            name = event.name,
            colorArgb = calculateEventColor(event.startDate, event.endDate)
        )
    }
