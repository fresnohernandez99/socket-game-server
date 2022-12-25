package views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun TextInfo(users: Int, rooms: Int) {
    Column {
        Text("Connected users: ${users}")
        Text("Available rooms: ${rooms}")
    }
}