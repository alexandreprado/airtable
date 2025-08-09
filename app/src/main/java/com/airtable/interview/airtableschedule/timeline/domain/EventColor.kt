package com.airtable.interview.airtableschedule.timeline.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import java.time.LocalDate
import kotlin.math.min

/**
 * Returns an ARGB int for the event background color, based on its duration.
 * Shorter events → cooler colors, longer events → warmer colors.
 */
fun calculateEventColor(startDate: LocalDate, endDate: LocalDate): Int {
    val days = ((endDate.toEpochDay() - startDate.toEpochDay()) + 1).toInt()
    val clampedDays = min(days, 30) // Cap at 30 days for color mapping

    // Map 1..30 days to a hue range (e.g. blue → red)
    val hue = 220f - (clampedDays / 30f) * 200f // 220 (blue) down to 20 (red)
    val saturation = 0.6f
    val lightness = 0.5f

    return hslToColor(hue, saturation, lightness).toArgb()
}

/** Converts HSL to a Compose Color */
private fun hslToColor(hue: Float, saturation: Float, lightness: Float): Color {
    val c = (1f - kotlin.math.abs(2f * lightness - 1f)) * saturation
    val x = c * (1f - kotlin.math.abs((hue / 60f) % 2f - 1f))
    val m = lightness - c / 2f

    val (r1, g1, b1) = when {
        hue < 60f -> Triple(c, x, 0f)
        hue < 120f -> Triple(x, c, 0f)
        hue < 180f -> Triple(0f, c, x)
        hue < 240f -> Triple(0f, x, c)
        hue < 300f -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }

    return Color(
        red = (r1 + m).coerceIn(0f, 1f),
        green = (g1 + m).coerceIn(0f, 1f),
        blue = (b1 + m).coerceIn(0f, 1f)
    )
}
