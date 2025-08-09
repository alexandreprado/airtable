package com.airtable.interview.airtableschedule.timeline.domain

data class CumHeights(val cumPx: IntArray)

/**
 * cumPx[i] = pixel Y of top of item i (0-based); cumPx[size] = total height
 */

fun buildCumHeights(itemHeightsPx: IntArray): CumHeights {
    val cum = IntArray(itemHeightsPx.size + 1)
    for (i in itemHeightsPx.indices) cum[i + 1] = cum[i] + itemHeightsPx[i]
    return CumHeights(cum)
}

fun indexOffsetAtY(cum: CumHeights, y: Int): Pair<Int, Int> {
    val a = cum.cumPx
    var lo = 0
    var hi = a.size - 1
    while (lo < hi) {
        val mid = (lo + hi) ushr 1
        if (a[mid] <= y) lo = mid + 1 else hi = mid
    }
    val idx = (lo - 1).coerceAtLeast(0)
    val off = (y - a[idx]).coerceAtLeast(0)
    return idx to off
}
