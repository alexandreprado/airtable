package com.airtable.interview.airtableschedule.models

import java.time.LocalDate

object SampleTimelineItems {
    private val year = 2025
    var timelineItems: List<Event> = listOf(
        Event(
            1,
            LocalDate.of(year, 1, 1),
            LocalDate.of(year, 1, 5),
            "First item"
        ),
        Event(
            2,
            LocalDate.of(year, 1, 2),
            LocalDate.of(year, 1, 8),
            "Second item"
        ),
        Event(
            3,
            LocalDate.of(year, 1, 6),
            LocalDate.of(year, 1, 13),
            "Another item"
        ),
        Event(
            4,
            LocalDate.of(year, 1, 14),
            LocalDate.of(year, 1, 14),
            "Another item"
        ),
        Event(
            5,
            LocalDate.of(year, 2, 1),
            LocalDate.of(year, 2, 15),
            "Third item"
        ),
        Event(
            6,
            LocalDate.of(year, 1, 12),
            LocalDate.of(year, 2, 16),
            "Fourth item with a super long name"
        ),
        Event(
            7,
            LocalDate.of(year, 2, 1),
            LocalDate.of(year, 2, 2),
            "Fifth item with a super long name"
        ),
        Event(
            8,
            LocalDate.of(year, 1, 3),
            LocalDate.of(year, 1, 5),
            "First item"
        ),
        Event(
            9,
            LocalDate.of(year, 1, 4),
            LocalDate.of(year, 1, 8),
            "Second item"
        ),
        Event(
            10,
            LocalDate.of(year, 1, 6),
            LocalDate.of(year, 1, 13),
            "Another item"
        ),
        Event(
            11,
            LocalDate.of(year, 1, 9),
            LocalDate.of(year, 1, 9),
            "Another item"
        ),
        Event(
            12,
            LocalDate.of(year, 2, 1),
            LocalDate.of(year, 2, 15),
            "Third item"
        ),
        Event(
            13,
            LocalDate.of(year, 1, 12),
            LocalDate.of(year, 2, 16),
            "Fourth item with a super long name"
        ),
        Event(
            14,
            LocalDate.of(year, 2, 1),
            LocalDate.of(year, 2, 1),
            "Fifth item with a super long name"
        )
    )
}
