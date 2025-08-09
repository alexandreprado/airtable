package com.airtable.interview.airtableschedule.timeline.domain

import java.time.LocalDate
import java.util.PriorityQueue

data class PlacedEvent(
    val event: TimeLineEvent,
    val lane: Int
)

internal fun assignLanes(events: List<TimeLineEvent>): List<PlacedEvent> {
    if (events.isEmpty()) return emptyList()
    val sortedEvents = events
        .sortedWith(compareBy<TimeLineEvent> { it.startDate }.thenBy { it.endDate })

    data class LaneEnd(val lane: Int, val end: LocalDate)

    val heap = PriorityQueue<LaneEnd>(compareBy { it.end })
    val laneEnds = mutableListOf<LocalDate>()
    val placed = mutableListOf<PlacedEvent>()

    for (event in sortedEvents) {
        val reusable = if (heap.isNotEmpty() && heap.peek().end.isBefore(event.startDate)) {
            heap.poll()
        } else {
            null
        }
        val laneIndex = if (reusable != null) {
            laneEnds[reusable.lane] = event.endDate
            heap.add(LaneEnd(reusable.lane, event.endDate))
            reusable.lane
        } else {
            val newLane = laneEnds.size
            laneEnds.add(event.endDate)
            heap.add(LaneEnd(newLane, event.endDate))
            newLane
        }
        placed += PlacedEvent(event, laneIndex)
    }
    return placed
}
