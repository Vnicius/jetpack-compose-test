package io.vnicius.github.composelayout

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.vnicius.github.composelayout.ui.theme.ComposeLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val messages = listOf(
                Message("Android", "Jetpack Compose"),
                Message("iOS", "SwiftUI"),
                Message("Web", "WOWOWOWOWOWOWOWOW"),
                Message(
                    "C",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eu turpis a neque placerat rutrum. Vivamus ligula leo, iaculis eget malesuada id, viverra et velit. Aenean consectetur laoreet libero. Donec sed neque non nisi dapibus pellentesque eu in dolor. Nulla gravida, eros ac porttitor elementum, purus tortor tincidunt orci, et vestibulum orci leo vel orci. Donec a felis ullamcorper, venenatis nulla ut, sagittis velit. Ut sed neque blandit, finibus nibh ac, lacinia turpis. Nullam ex velit, gravida facilisis enim et, euismod eleifend sem. Duis blandit, ante non dictum porttitor, eros magna suscipit urna, at faucibus justo quam sed turpis. Morbi scelerisque elit eros, nec tristique turpis congue sit amet. Aliquam ipsum arcu, consectetur in aliquam vitae, ullamcorper ut leo."
                )
            )

            ComposeLayoutTheme {
                HomeScreen(messages)
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun HomeScreen(messages: List<Message>) {
    Conversation(messages)
}

@Composable
fun MessageCard(message: Message) {
    Row {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = message.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = message.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Preview(showBackground = true, fontScale = 2f, name = "Big Font")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, fontScale = .2f, name = "Small Font")
@Composable
fun DefaultPreview() {
    ComposeLayoutTheme {
        HomeScreen(
            listOf(
                Message("Author1", "This is a message ok?"),
                Message("Author2", "What? This is a real message from a real person")
            )
        )
    }
}