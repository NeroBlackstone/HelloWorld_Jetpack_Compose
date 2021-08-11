package com.example.learn_jetpack

import android.content.res.Configuration
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
import com.example.learn_jetpack.ui.theme.Learn_JetpackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Composes the given composable into the given activity.
        setContent {
            Learn_JetpackTheme {
                Conversation(SampleData.conversationSample)
            }
        }
    }
}

data class Message(val author: String, val body: String)

// Composable functions are the fundamental building blocks of an application built with Compose.
@Composable
fun MessageCard(msg: Message) {
    // Add padding around our message
    Row (modifier = Modifier.padding(all = 8.dp)) {
        // Creates a composable that lays out and draws a given Painter.
        Image(
            // Create a Painter from an Android resource id.
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this variable
        var isExpanded by remember { mutableStateOf(false) }

        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor: Color by animateColorAsState(
            // The primary color is the color displayed most frequently across your app’s screens
            // and components.
            // The surface color is used on surfaces of components, such as cards, sheets and menus.
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        // We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            // High level element that displays text and provides semantics / accessibility
            // information.
            Text(
                text = msg.author,
                // The secondary variant color is used to distinguish two elements of the app using
                // the secondary color.
                color = MaterialTheme.colors.secondaryVariant,
                // Retrieves the current Typography at the call site's position in the hierarchy.
                // subtitle2 is the smallest subtitle, and is typically reserved for medium-emphasis
                // text that is shorter in length.
                style = MaterialTheme.typography.subtitle2
            )
            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))

            // Material Design surface.
            Surface(
                // Retrieves the current Shapes at the call site's position in the hierarchy.
                // Shape used by medium components like Card or AlertDialog.
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier.animateContentSize().padding(1.dp),
            ) {
                Text(
                    text = msg.body,
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    modifier = Modifier.padding(all = 4.dp),
                    // body2 is the smallest body, and is typically used for long-form writing as it
                    // works well for small text sizes.
                    style = MaterialTheme.typography.body2
                )
            }
        }

    }
}

// Preview can be applied to @Composable methods with no parameters to show them in the Android
// Studio preview.
@Preview(name = "Light Mode")
@Preview(
    // This class describes all device configuration information that can impact the resources the
    // application retrieves.
    // Constant for uiMode: a UI_MODE_NIGHT_MASK value that corresponds to the night resource
    // qualifier
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    Learn_JetpackTheme {
        MessageCard(
            msg = Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!")
        )
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    // The vertically scrolling list that only composes and lays out the currently visible items.
    LazyColumn {
        // Adds a list of items.
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    Learn_JetpackTheme {
        Conversation(SampleData.conversationSample)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Learn_JetpackTheme {
        Greeting("Android")
    }
}