package io.vnicius.github.composelayoutcodelab

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.google.android.material.chip.Chip
import io.vnicius.github.composelayoutcodelab.ui.theme.ComposeLayoutCodelabTheme
import kotlin.math.max


/**
 * Created by Vinícius Veríssimo on 17/08/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->

        // Keep track of rows widths
        val rowWidths = IntArray(rows) { 0 }

        // Keep track of rows max height
        val rowHeights = IntArray(rows) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->
            // Measure child
            val placeable = measurable.measure(constraints)

            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = max(rowHeights[row], placeable.height)

            placeable
        }

        val width = rowWidths.maxOrNull()?.coerceIn(constraints.minWidth, constraints.maxWidth)
            ?: constraints.minWidth
        val height = rowHeights.sum().coerceIn(constraints.minHeight, constraints.maxHeight)
        
        // Calculate the Y position of each row
        val rowsY = IntArray(rows) { 0 }
        
        for (index in 1.until(rows)) {
            rowsY[index] = rowsY[index - 1] + rowHeights[index - 1]
        }

        layout(width, height) {
            val rowsX = IntArray(rows) { 0 }
            
            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                
                placeable.placeRelative(
                    x = rowsX[row],
                    y = rowsY[row]
                )
                
                rowsX[row] += placeable.width
            }
        }
    }
}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, bottom = 4.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text, style = MaterialTheme.typography.caption)
        }
    }
}

@Preview(name = "Chip Preview", showBackground = true)
@Composable
fun ChipPreview() {
    ComposeLayoutCodelabTheme {
        Chip(text = "This is a chip")
    }
}

@Preview(name = "Grid Preview", showBackground = true)
@Composable
fun GridPreview() {
    ComposeLayoutCodelabTheme {
        StaggeredGrid(rows = 5) {
            topics.forEach { 
                Chip(text = it)
            }
        }
    }
}