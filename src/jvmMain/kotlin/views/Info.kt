package views

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun TextInfo(serverState: Int) {
    Column {
        Text("Connected users: ${serverState}")
    }
}