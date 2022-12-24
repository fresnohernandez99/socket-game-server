import androidx.compose.runtime.mutableStateListOf
import socket.model.Message
import socket.coder.MessageDecoder
import socket.coder.MessageEncoder
import socket.model.Room
import java.io.IOException
import java.util.function.Consumer
import javax.websocket.*
import javax.websocket.EncodeException
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint

@ServerEndpoint(
    value = "/server/{username}",
    decoders = [MessageDecoder::class],
    encoders = [MessageEncoder::class]
)
class SocketEndpoint {
    private var session: Session? = null

    @OnOpen
    @Throws(IOException::class)
    fun onOpen(
        session: Session,
        @PathParam("username") username: String
    ) {
        this.session = session
        socketEndpoints.add(this)
        users[session.id] = username
        val message = Message()
        message.from = username
        message.content = "Connected!"
        broadcast(message)
    }

    @OnMessage
    @Throws(IOException::class)
    fun onMessage(session: Session, message: Message) {
        message.from = users[session.id]
        broadcast(message)
    }

    @OnClose
    @Throws(IOException::class)
    fun onClose(session: Session) {
        socketEndpoints.remove(this)
        val message = Message()
        message.from = users[session.id]
        message.content = "Disconnected!"
        broadcast(message)
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

    companion object {
        val socketEndpoints = mutableStateListOf<SocketEndpoint>()
        val rooms = mutableStateListOf<Room>()
        val users: HashMap<String, String> = HashMap()
    }
}