package com.jetpack.kawanusaha.data

import java.util.*

data class MessageSource (
    val text: String? = null,
    val recipientId: String,
    val time: Long = Calendar.getInstance().timeInMillis,
    val isOut: Boolean = false
)

val message_dummy = listOf(
    MessageSource(
        text = "In this article I am going to cover how I’ve implemented a form using Jetpack Compose but “focusing” more on text field focus and switching between text fields with IME actions using the keyboard, at a basic level. \n \n" +
                "To start, I have created a ViewModel that emits a view state using StateFlow with the form fields that are required along with a string of the user’s input to show the full string from all the input fields. A function has also been added that takes the users input and updates the relevant field with the new value. Without this, the text field will not show any of the text the user is entering using the keyboard. An alternative to this would be to use remember if you did not want to use a ViewModel.",
        recipientId = "bot",
        isOut = false
    ),
    MessageSource(
        text = "I am good",
        recipientId = "user",
        isOut = true
    ),
    MessageSource(
        text = "yeah!",
        recipientId = "user",
        isOut = false
    )
)