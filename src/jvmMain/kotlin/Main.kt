// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                Button(content = {
                    Text("Close all rooms")
                }, onClick = {
                    server?.cleanRooms()
                })

                Spacer(modifier = Modifier.width(20.dp))

                Button(content = {
                    Text("Kick all")
                }, onClick = {
                    server?.cleanServer()
                })

                Spacer(modifier = Modifier.width(20.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red,
                        contentColor = Color.White
                    ),
                    content = {
                        Text("Stop server")
                    }, onClick = {
                        server?.stopServer()
                    })

                Spacer(modifier = Modifier.width(20.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Green,
                        contentColor = Color.White
                    ),
                    content = {
                        Text("Start server")
                    }, onClick = {
                        server?.startServer()
                    })

                Spacer(modifier = Modifier.width(20.dp))

            }

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
    Window(
        title = "Game Server 0.0.1-alpha",
        onCloseRequest = ::exitApplication,
        resizable = false
    ) {
        App()
    }
}
