package io.vnicius.github.composelayoutcodelab

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.vnicius.github.composelayoutcodelab.ui.theme.ComposeLayoutCodelabTheme


/**
 * Created by Vinícius Veríssimo on 16/08/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        // Check if had a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // Height of the composable with padding - first baseline
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY

        layout(placeable.width, height) {
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Composable
fun MyCustomColumn(modifier: Modifier, content: @Composable () -> Unit) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        // Measure children
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        // Next child y position
        var yPosition = 0

        // Make the size of the parent
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(0, yPosition)
                yPosition += placeable.height
            }
        }
    }
}


@Preview(name = "Padding to Baseline")
@Composable
fun TextWithPaddingToBaselinePreview() {
    ComposeLayoutCodelabTheme {
        Text(text = "Hi there!", modifier = Modifier.firstBaselineToTop(32.dp))
    }
}

@Preview(name = "Padding")
@Composable
fun TextWithNormalPaddingPreview() {
    ComposeLayoutCodelabTheme {
        Text(text = "Hi there!", modifier = Modifier.padding(top = 32.dp))
    }
}