package socket

import SocketEndpoint
import com.google.gson.Gson
import socket.Constants.INTENT_CORRECT
import socket.Constants.INTENT_WITH_ERROR
import socket.model.Message
import socket.model.Room
import socket.model.request.JoinRoomRequest
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
        message.content = Response<Any>(INTENT_CORRECT).toJson()
        return message
    }

    fun reConnecting() {
        //TODO
    }

    fun createRoom(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val room = Gson().fromJson(msg.content, Room::class.java)
        room.owner = session.id
        room.users = ArrayList()
        room.users?.add(session.id)

        SocketEndpoint.rooms.add(room)

        val message = Message(Constants.INTENT_CREATE_ROOM)
        message.from = session.id
        message.to = session.id
        message.content = Response(INTENT_CORRECT, null).toJson()
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
        message.roomId = request.roomId
        message.content = Response(INTENT_CORRECT, null).toJson()
        return message
    }

    fun cancelRoom(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val request = Gson().fromJson(msg.content, StartRoomRequest::class.java)

        val findingRoom = SocketEndpoint.rooms.find { it.id == request.roomId && it.owner == request.hostId }

        if (findingRoom != null) {
            SocketEndpoint.rooms.remove(findingRoom)
        }

        val message = Message(Constants.INTENT_CANCEL_ROOM)
        message.from = session.id
        message.roomId = request.roomId
        message.content = Response(INTENT_CORRECT, null).toJson()
        return message
    }

    fun getRooms(socketEndpoint: SocketEndpoint, session: Session): Message {
        val message = Message(Constants.INTENT_GET_ROOMS)
        message.from = session.id
        message.to = session.id
        message.content = Response(INTENT_CORRECT, SocketEndpoint.rooms.toList()).toJson()
        return message
    }

    fun joinRoom(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val request = Gson().fromJson(msg.content, JoinRoomRequest::class.java)

        val findingRoom = SocketEndpoint.rooms.find {
            it.id == request.roomId &&
                    !(it.users!!.contains(session.id)) &&
                    it.code == request.code
        }

        val message = Message(Constants.INTENT_JOIN_ROOM)

        if (findingRoom != null) {
            findingRoom.users!!.add(session.id)
            message.from = session.id
            message.roomId = findingRoom.id
            message.content = Response(INTENT_CORRECT, findingRoom).toJson()
        } else {
            message.from = session.id
            message.to = session.id
            message.content = Response(INTENT_WITH_ERROR, null).toJson()
        }

        return message
    }

    //TODO
    fun updateRoomConfig() {}


}