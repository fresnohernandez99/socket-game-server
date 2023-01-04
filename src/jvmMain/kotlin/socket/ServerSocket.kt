package socket

import SocketEndpoint
import engine.GameEngine
import model.terrain.Terrain
import model.terrain.space.DefeatCause
import model.terrain.space.Space
import model.terrain.space.SpaceConfiguration
import org.glassfish.tyrus.server.Server
import java.io.BufferedReader
import java.io.InputStreamReader


class ServerSocket {
    val socket = Server("localhost", 8025, "/", SocketEndpoint::class.java)

    suspend fun initServer() {
        // TODO load JSON configurations from file

        // start socket server
        try {
            socket.start()
            println("--- server is running")
            val bufferRead = BufferedReader(InputStreamReader(System.`in`))
            bufferRead.readLine()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            socket.stop()
        }
    }

    fun cleanRooms() {
        SocketEndpoint.rooms.clear()
    }

    fun cleanServer() {
        SocketEndpoint.rooms.clear()
        SocketEndpoint.socketEndpoints.clear()
        SocketEndpoint.users.clear()
    }

    fun startServer() {
        socket.start()
    }

    fun stopServer() {
        socket.stop()
        SocketEndpoint.rooms.clear()
        SocketEndpoint.socketEndpoints.clear()
        SocketEndpoint.users.clear()
    }

}