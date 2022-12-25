// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import socket.ServerSocket
import views.TextInfo

@Composable
@Preview
fun App() {
    var server: ServerSocket? = null
    val users = SocketEndpoint.socketEndpoints
    val rooms = SocketEndpoint.rooms

    MaterialTheme {
        Column {
            if (users.size != 0) TextInfo(users.size, rooms.size) else Text("No hay conectados")
            Button(content = {
                Text("Stop server")
            }, onClick = {
                server?.stopServer()
            })
        }
    }

    LaunchedEffect(false, null) {
        GlobalScope.launch(Dispatchers.IO) {
            server = ServerSocket()
            server?.initServer()
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
