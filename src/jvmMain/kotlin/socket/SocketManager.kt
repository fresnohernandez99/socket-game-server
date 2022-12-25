package socket

import SocketEndpoint
import com.google.gson.Gson
import socket.model.Message
import socket.model.Room
import socket.model.response.Response
import javax.websocket.Session

object SocketManager {
    fun connecting(socketEndpoint: SocketEndpoint, session: Session, username: String): Message {
        SocketEndpoint.socketEndpoints.add(socketEndpoint)
        SocketEndpoint.users[session.id] = username
        val message = Message(Constants.INTENT_CONNECTING)
        message.from = username
        message.to = session.id
        message.content = Response(200, "aa", null).toJson()
        return message
    }

    fun reConnecting() {
        //TODO
    }

    fun createRoom(socketEndpoint: SocketEndpoint, session: Session, msg: Message){
        val room = Gson().fromJson(msg.content, Room::class.java)
        SocketEndpoint.rooms.add(room)
    }
}