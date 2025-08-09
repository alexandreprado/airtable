package com.airtable.interview.airtableschedule.timeline.domain

import java.time.LocalDate

data class TimeLineEvent(
    val id: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val name: String,
    val colorArgb: Int? = null
)
