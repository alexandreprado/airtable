package com.airtable.interview.airtableschedule.models

import java.time.LocalDate

data class Event(val id: Int, val startDate: LocalDate, val endDate: LocalDate, val name: String)
