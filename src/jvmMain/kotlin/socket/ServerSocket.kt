package socket

import SocketEndpoint
import model.terrain.Terrain
import model.terrain.space.DefeatCause
import model.terrain.space.Space
import model.terrain.space.SpaceConfiguration
import org.glassfish.tyrus.server.Server
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket


class ServerSocket {
    val socket = Server("localhost", 8025, "/", SocketEndpoint::class.java)

    suspend fun initServer() {
        // TODO load JSON configurations from file

        // init configurations
        serverConfigurations = Terrain(
            space = Space(
                SpaceConfiguration(
                    defeatCause = DefeatCause.SINGLE_PIECE_DEFEATED
                )
            ),
            rules = ArrayList()
        )

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

    fun stopServer() {
        socket.stop()
        SocketEndpoint.rooms.clear()
        SocketEndpoint.socketEndpoints.clear()
        SocketEndpoint.users.clear()
    }

    companion object {
        lateinit var serverConfigurations: Terrain
    }
}