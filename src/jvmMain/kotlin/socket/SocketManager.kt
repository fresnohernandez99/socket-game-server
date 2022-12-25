package socket

import SocketEndpoint
import com.google.gson.Gson
import socket.Constants.INTENT_CORRECT
import socket.model.Message
import socket.model.Room
import socket.model.request.StartRoomRequest
import socket.model.response.Response
import javax.websocket.Session

object SocketManager {
    fun connecting(socketEndpoint: SocketEndpoint, session: Session, username: String): Message {
        SocketEndpoint.socketEndpoints.add(socketEndpoint)
        SocketEndpoint.users[session.id] = username
        val message = Message(Constants.INTENT_CONNECTING)
        message.from = session.id
        message.to = session.id
        message.content = Response(200, INTENT_CORRECT, null).toJson()
        return message
    }

    fun reConnecting() {
        //TODO
    }

    fun createRoom(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val room = Gson().fromJson(msg.content, Room::class.java)
        room.owner = session.id
        room.users.add(session.id)
        SocketEndpoint.rooms.add(room)

        val message = Message(Constants.INTENT_CREATE_ROOM)
        message.from = session.id
        message.to = session.id
        message.content = Response(200, INTENT_CORRECT, null).toJson()
        return message
    }

    fun closeRoom(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val request = Gson().fromJson(msg.content, StartRoomRequest::class.java)

        val findingRoom = SocketEndpoint.rooms.find { it.id == request.roomId && it.owner == request.hostId }

        if (findingRoom != null) {
            findingRoom.closed = true
        }

        val message = Message(Constants.INTENT_CLOSE_ROOM)
        message.from = session.id
        message.to = request.roomId
        message.content = Response(200, INTENT_CORRECT, null).toJson()
        return message
    }
}