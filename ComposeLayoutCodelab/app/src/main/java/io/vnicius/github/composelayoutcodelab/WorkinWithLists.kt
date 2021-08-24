package io.vnicius.github.composelayoutcodelab

import android.view.Surface
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import io.vnicius.github.composelayoutcodelab.ui.theme.ComposeLayoutCodelabTheme
import kotlinx.coroutines.launch


/**
 * Created by Vinícius Veríssimo on 16/08/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */


@Composable
fun ImageListItem(index: Int) {
    Surface(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberImagePainter(
                    data = "https://developer.android.com/images/brand/Android_Robot.png"
                ),
                contentDescription = "Android logo",
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Item #$index", style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Composable
fun ImageList(size: Int) {
    val scrollState = rememberLazyListState()

    Column {
        ScrollButtons(scrollState)

        LazyColumn(state = scrollState) {
            items(size) {
                ImageListItem(it)
            }
        }
    }

}

@Composable
fun ScrollButtons(state: LazyListState) {
    val coroutineScope = rememberCoroutineScope()

    Row {
       Button(onClick = {
           coroutineScope.launch {
               state.animateScrollToItem(0)
           }
       }) {
           Text(text = "Scroll to the top")
       }

        Button(onClick = {
            coroutineScope.launch {
                state.animateScrollToItem(state.layoutInfo.totalItemsCount - 1)
            }
        }) {
            Text(text = "Scroll to the end")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageListItemPreview() {
    ComposeLayoutCodelabTheme {
        ImageListItem(0)
    }
}