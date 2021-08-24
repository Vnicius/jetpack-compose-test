package io.vnicius.github.composelayoutcodelab

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import io.vnicius.github.composelayoutcodelab.ui.theme.ComposeLayoutCodelabTheme


/**
 * Created by Vinícius Veríssimo on 23/08/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // Create references
        val (button, text) = createRefs()

        Button(onClick = { /*Do something*/ },
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(parent.top, margin = 16.dp)
                }) {
            Text("Button")
        }

        Text(text = "Text", Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)
        })
    }
}

@Composable
fun ConstraintLayoutContent2() {
    ConstraintLayout {
        // Create references
        val (button1, button2, text) = createRefs()

        Button(onClick = { /*Do something*/ },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }) {
            Text("Button 1")
        }

        Text(text = "Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        val barrier = createEndBarrier(button1, text)

        Button(
            onClick = { /*Do something*/ },
        modifier = Modifier.constrainAs(button2) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(barrier)
        }) {
            Text(text = "Button 2")
        }
    }
}

@Composable
fun LargerConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        val guideline = createGuidelineFromStart(fraction = .5f)

        Text(
            text = "This is a very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent
            })
    }
}

@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(16.dp)
        } else {
            decoupledConstraints(32.dp)
        }

        ConstraintLayout(constraintSet = constraints) {
            Button(onClick = { /*TODO*/ },  Modifier.layoutId("button")) {
                Text(text = "Button")
            }

            Text(text = "Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet = ConstraintSet {
    val button = createRefFor("button")
    val text = createRefFor("text")

    constrain(button) {
        top.linkTo(parent.top, margin = margin)
    }
    constrain(text) {
        top.linkTo(button.bottom, margin = margin)
    }
}

@Preview(showBackground = true)
@Composable
fun ConstraintLayoutPreview() {
    ComposeLayoutCodelabTheme {
        DecoupledConstraintLayout()
    }
}