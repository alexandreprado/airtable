package com.airtable.interview.airtableschedule.timeline.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EventBlock(
    name: String,
    colorArgb: Int?,
    modifier: Modifier
) {
    val bg = colorArgb?.let { Color(it) } ?: MaterialTheme.colorScheme.primaryContainer
    val fg = MaterialTheme.colorScheme.onPrimaryContainer
    val shape = RoundedCornerShape(10.dp)

    Box(
        modifier
            .background(bg, shape)
            .border(1.dp, MaterialTheme.colorScheme.outline, shape)
            .padding(8.dp)
    ) {
        Text(name, color = fg, style = MaterialTheme.typography.labelLarge, maxLines = 2)
    }
}
