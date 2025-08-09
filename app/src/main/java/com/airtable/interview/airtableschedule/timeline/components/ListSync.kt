package com.airtable.interview.airtableschedule.timeline.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import com.airtable.interview.airtableschedule.timeline.domain.CumHeights
import com.airtable.interview.airtableschedule.timeline.domain.indexOffsetAtY
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Bi-directional scroll sync
 */
@Composable
fun ListSync(
    railState: LazyListState,
    planeState: LazyListState,
    railCum: CumHeights,
    planeCum: CumHeights
) {
    val scope = rememberCoroutineScope()
    val isSyncing = remember { mutableStateOf(false) }
    val epsilon = 4

    fun absY(state: LazyListState, cum: CumHeights): Int {
        val i = state.firstVisibleItemIndex
        val off = state.firstVisibleItemScrollOffset
        return cum.cumPx[i] + off
    }

    LaunchedEffect(railState, planeState, railCum, planeCum) {
        // Sync left to right
        scope.launch {
            snapshotFlow { absY(railState, railCum) }.collectLatest { y ->
                if (isSyncing.value) return@collectLatest
                val currentY = absY(planeState, planeCum)
                if (kotlin.math.abs(currentY - y) <= epsilon) return@collectLatest
                val (idx, off) = indexOffsetAtY(planeCum, y)
                isSyncing.value = true
                planeState.scrollToItem(idx, off)
                isSyncing.value = false
            }
        }
        // Sync right to left
        scope.launch {
            snapshotFlow { absY(planeState, planeCum) }.collectLatest { y ->
                if (isSyncing.value) return@collectLatest
                val currentY = absY(railState, railCum)
                if (kotlin.math.abs(currentY - y) <= epsilon) return@collectLatest
                val (idx, off) = indexOffsetAtY(railCum, y)
                isSyncing.value = true
                railState.scrollToItem(idx, off)
                isSyncing.value = false
            }
        }
    }
}

