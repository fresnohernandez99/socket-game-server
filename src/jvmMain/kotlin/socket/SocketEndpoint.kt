import androidx.compose.runtime.mutableStateListOf
import socket.Constants.INTENT_CANCEL_ROOM
import socket.Constants.INTENT_CLOSE_ROOM
import socket.Constants.INTENT_CONNECTING
import socket.Constants.INTENT_CREATE_ROOM
import socket.Constants.INTENT_GET_ROOMS
import socket.Constants.INTENT_JOIN_ROOM
import socket.Constants.INTENT_RE_CONNECTING
import socket.Constants.INTENT_WITH_ERROR
import socket.SocketManager
import socket.coder.MessageDecoder
import socket.coder.MessageEncoder
import socket.model.Message
import socket.model.Room
import util.Logger
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

        autoSend(
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
                autoSend(SocketManager.createRoom(this, session, message))
            }

            INTENT_CLOSE_ROOM -> {
                val response = SocketManager.closeRoom(this, session, message)
                autoSend(response)
            }

            INTENT_CANCEL_ROOM -> {
                val response = SocketManager.cancelRoom(this, session, message)
                autoSend(response)
            }

            INTENT_GET_ROOMS -> {
                val response = SocketManager.getRooms(this, session)
                autoSend(response)
            }

            INTENT_JOIN_ROOM -> {
                val response = SocketManager.joinRoom(this, session, message)
                autoSend(response)
            }
        }

        //broadcast(message)
    }

    @OnClose
    @Throws(IOException::class)
    fun onClose(session: Session) {
        val userDisconnected = socketEndpoints.find { it.session?.id == session.id }
        socketEndpoints.remove(userDisconnected)

        val roomExists = rooms.find { it.id == session.id }
        if (roomExists != null) rooms.remove(roomExists)

        // TODO avisar a la room en la q estaba
        /*val message = Message()
        message.from = users[session.id]
        message.content = "Disconnected!"
        broadcast(message)*/
    }

    @OnError
    fun onError(session: Session?, throwable: Throwable?) {
        // Do error handling here
    }

    fun autoSend(message: Message) {
        if (message.broadcast) broadcast(message)

        if (message.roomId != null) sendToRoom(message)

        if (message.to != null) sendTo(message)
    }

    @Throws(IOException::class, EncodeException::class)
    private fun broadcast(message: Message) {
        Logger.log(TAG_SEND_BROADCAST, "", message)

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

        Logger.log(TAG_SEND_TO, message.to!!, message)

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
    private fun sendToRoom(message: Message) {
        val roomUsers = rooms.find { it.id == message.roomId }

        roomUsers?.let { room ->

            Logger.log(TAG_SEND_TO_ROOM, room.id, message)

            socketEndpoints.forEach(Consumer { endpoint: SocketEndpoint ->
                synchronized(endpoint) {
                    try {
                        val isInRoom = room.users?.find { it == endpoint.session!!.id }
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

        private const val TAG_SEND_TO = "Send-To"
        private const val TAG_SEND_TO_ROOM = "Send-To-Room"
        private const val TAG_SEND_BROADCAST = "Send-Broadcast"
    }
}