package com.airtable.interview.airtableschedule.timeline

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airtable.interview.airtableschedule.timeline.components.ListSync
import com.airtable.interview.airtableschedule.timeline.components.Plane
import com.airtable.interview.airtableschedule.timeline.components.Rail

/**
 * A screen that displays a timeline of events.
 */
@Composable
fun TimelineScreen(
    viewModel: TimelineViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    when (uiState) {
        is TimelineUiState.Idle -> Unit
        is TimelineUiState.Loading -> {}
        is TimelineUiState.Success -> Events(uiState.state)
        is TimelineUiState.Error -> {}
    }
}

@Composable
fun Events(state: EventsState) {
    val railState = rememberLazyListState()
    val planeState = rememberLazyListState()

    ListSync(
        railState = railState,
        planeState = planeState,
        railCum = state.railCum,
        planeCum = state.planeCum
    )

    Row(Modifier.fillMaxSize()) {
        Rail(
            sections = state.sections,
            listState = railState,
            railWidth = railWidth,
            monthHeaderHeight = monthHeaderHeight,
            dayRowHeight = dayRowHeight
        )
        Plane(
            state = state,
            listState = planeState,
            monthHeaderHeight = monthHeaderHeight,
            dayRowHeight = dayRowHeight,
            laneWidth = laneWidth,
            laneSpacing = laneSpacing,
            eventVPad = eventVPad
        )
    }
}

// Ideally we'd move these into config files
private val railWidth = 110.dp
private val monthHeaderHeight = 40.dp
private val dayRowHeight = 36.dp
private val laneWidth = 220.dp
private val laneSpacing = 12.dp
private val eventVPad = 2.dp
