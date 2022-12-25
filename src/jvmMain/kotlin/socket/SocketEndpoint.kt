import androidx.compose.runtime.mutableStateListOf
import model.terrain.Terrain
import socket.Constants.INTENT_CANCEL_ROOM
import socket.Constants.INTENT_CLOSE_ROOM
import socket.Constants.INTENT_CONNECTING
import socket.Constants.INTENT_CREATE_ROOM
import socket.Constants.INTENT_RE_CONNECTING
import socket.Constants.INTENT_WITH_ERROR
import socket.SocketManager
import socket.coder.MessageDecoder
import socket.coder.MessageEncoder
import socket.model.Message
import socket.model.Room
import java.io.IOException
import java.util.function.Consumer
import javax.websocket.*
import javax.websocket.EncodeException
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint

@ServerEndpoint(
    value = "/server/{intent}/{username}", decoders = [MessageDecoder::class], encoders = [MessageEncoder::class]
)
class SocketEndpoint {
    private var session: Session? = null

    @OnOpen
    @Throws(IOException::class)
    fun onOpen(
        session: Session, @PathParam("username") username: String, @PathParam("intent") intent: String
    ) {
        this.session = session

        sendTo(
            when (intent) {
                INTENT_CONNECTING -> SocketManager.connecting(this, session, username)
                INTENT_RE_CONNECTING -> SocketManager.connecting(this, session, username)
                // TODO reconnecting
                else -> Message(INTENT_WITH_ERROR, to = session.id)
            }
        )

        //broadcast(message)
    }

    @OnMessage
    @Throws(IOException::class)
    fun onMessage(session: Session, message: Message) {
        message.from = users[session.id]

        when (message.endpoint) {
            INTENT_CREATE_ROOM -> {
                sendTo(SocketManager.createRoom(this, session, message))
            }

            INTENT_CLOSE_ROOM -> {
                val response = SocketManager.closeRoom(this, session, message)
                sendToRoom(response.to!!, response)

                //enviar configuracion de partida

            }

            INTENT_CANCEL_ROOM -> {
                val response = SocketManager.cancelRoom(this, session, message)
                sendToRoom(response.to!!, response)
            }
        }

        //broadcast(message)
    }

    @OnClose
    @Throws(IOException::class)
    fun onClose(session: Session) {
        socketEndpoints.remove(this)/*val message = Message()
        message.from = users[session.id]
        message.content = "Disconnected!"
        broadcast(message)*/
    }

    @OnError
    fun onError(session: Session?, throwable: Throwable?) {
        // Do error handling here
    }

    @Throws(IOException::class, EncodeException::class)
    private fun broadcast(message: Message) {
        socketEndpoints.forEach(Consumer { endpoint: SocketEndpoint ->
            synchronized(endpoint) {
                try {
                    endpoint.session?.basicRemote?.sendObject(message)
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: EncodeException) {
                    e.printStackTrace()
                }
            }
        })

    }

    @Throws(IOException::class, EncodeException::class)
    private fun sendTo(message: Message) {
        socketEndpoints.forEach(Consumer { endpoint: SocketEndpoint ->
            synchronized(endpoint) {
                try {
                    if (endpoint.session!!.id == message.to) endpoint.session?.basicRemote?.sendObject(message)
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: EncodeException) {
                    e.printStackTrace()
                }
            }
        })
    }

    @Throws(IOException::class, EncodeException::class)
    private fun sendToRoom(roomId: String, message: Message) {
        val roomUsers = rooms.find { it.id == roomId }

        roomUsers?.let { room ->

            socketEndpoints.forEach(Consumer { endpoint: SocketEndpoint ->
                synchronized(endpoint) {
                    try {
                        val isInRoom = room.users.find { it == endpoint.session!!.id }
                        if (isInRoom != null) endpoint.session?.basicRemote?.sendObject(message)

                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: EncodeException) {
                        e.printStackTrace()
                    }
                }
            })
        }
    }

    companion object {
        val socketEndpoints = mutableStateListOf<SocketEndpoint>()
        val rooms = mutableStateListOf<Room>()
        val users: HashMap<String, String> = HashMap()
    }
}