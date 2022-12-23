package socket

import SocketEndpoint
import org.glassfish.tyrus.server.Server
import java.io.BufferedReader
import java.io.InputStreamReader


class ServerSocket {
    init {
        val server = Server("localhost", 8025, "/", SocketEndpoint::class.java)

        try {
            server.start()
            println("--- server is running")
            println("--- press any key to stop the server")
            val bufferRead = BufferedReader(InputStreamReader(System.`in`))
            bufferRead.readLine()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            server.stop()
        }
    }

    companion object {

    }
}