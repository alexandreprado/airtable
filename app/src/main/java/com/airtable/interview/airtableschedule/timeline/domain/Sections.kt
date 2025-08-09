package com.airtable.interview.airtableschedule.timeline.domain

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

data class TimelineBounds(
    val minDate: LocalDate,
    val maxDate: LocalDate
)

data class MonthSection(
    val ym: YearMonth,
    val days: List<LocalDate>
)

fun computeBounds(events: List<TimeLineEvent>): TimelineBounds {
    val min = events.minOf { it.startDate }
    val max = events.maxOf { it.endDate }
    return TimelineBounds(min, max)
}

fun daysInclusive(start: LocalDate, end: LocalDate): Int =
    (end.toEpochDay() - start.toEpochDay()).toInt() + 1

fun buildMonthSections(bounds: TimelineBounds): List<MonthSection> {
    var cur = YearMonth.from(bounds.minDate)
    val last = YearMonth.from(bounds.maxDate)
    val out = mutableListOf<MonthSection>()
    while (!cur.isAfter(last)) {
        val start = maxOf(bounds.minDate, cur.atDay(1))
        val end = minOf(bounds.maxDate, cur.atEndOfMonth())
        val total = daysInclusive(start, end)
        val days = List(total) { i -> start.plusDays(i.toLong()) }
        out += MonthSection(cur, days)
        cur = cur.plusMonths(1)
    }
    return out
}

val MonthHeaderFmt: DateTimeFormatter
    get() = DateTimeFormatter.ofPattern("LLLL yyyy", Locale.getDefault())

val DayHeaderFmt: DateTimeFormatter
    get() = DateTimeFormatter.ofPattern("EEE, dd MMM", Locale.getDefault())
