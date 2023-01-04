package socket

import SocketEndpoint
import com.google.gson.Gson
import model.player.HumanPlayer
import socket.Constants.INTENT_CORRECT
import socket.Constants.INTENT_WITH_ERROR
import socket.model.Message
import socket.model.Room
import socket.model.request.GetUsersInfoRequest
import socket.model.request.JoinRoomRequest
import socket.model.request.StartRoomRequest
import socket.model.request.UploadUserInfoRequest
import socket.model.response.AbandonRoomResponse
import socket.model.response.Response
import util.JSON
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

        val exists = SocketEndpoint.rooms.find { it.id == room.id }
        if (exists != null) SocketEndpoint.rooms.remove(exists)

        SocketEndpoint.rooms.add(room)

        val message = Message(Constants.INTENT_CREATE_ROOM)
        message.from = session.id
        message.to = session.id
        message.content = Response(INTENT_CORRECT, room).toJson()
        return message
    }

    fun closeRoom(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val request = Gson().fromJson(msg.content, StartRoomRequest::class.java)

        val findingRoom = SocketEndpoint.rooms.find { it.id == request.roomId && it.owner == session.id }

        if (findingRoom != null) {
            findingRoom.closed = true

            SocketEndpoint.roomTerrains[findingRoom.id] = ServerSocket.gameEngine.copy()
        }

        val message = Message(Constants.INTENT_CLOSE_ROOM)
        message.from = session.id
        message.roomId = request.roomId
        message.content = Response(INTENT_CORRECT, null).toJson()
        return message
    }

    fun abandonRoom(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val request = Gson().fromJson(msg.content, StartRoomRequest::class.java)

        val findingRoomIndex = SocketEndpoint.rooms.indexOfFirst { it.id == request.roomId }

        var roomId = ""

        if (findingRoomIndex != -1) {
            roomId = SocketEndpoint.rooms[findingRoomIndex].id
            if (SocketEndpoint.rooms[findingRoomIndex].owner == session.id) {
                SocketEndpoint.rooms.removeAt(findingRoomIndex)
            } else {
                SocketEndpoint.rooms[findingRoomIndex].users!!.removeAll { it == session.id }
            }
        }

        val message = Message(Constants.INTENT_ABANDON_ROOM)
        message.from = session.id
        message.broadcast = true
        message.content = Response(INTENT_CORRECT, AbandonRoomResponse(session.id, roomId)).toJson()
        return message
    }

    fun getRooms(socketEndpoint: SocketEndpoint, session: Session): Message {
        val listToSend = ArrayList<Room>()

        listToSend.addAll(SocketEndpoint.rooms.toList())

        listToSend.forEach {
            it.code = ""

            if (it.closed) listToSend.remove(it)
            else if (ServerSocket.gameEngine.getConfigurations().space.configuration.maxPlayers == it.users!!.size) listToSend.remove(
                it
            )
        }

        val message = Message(Constants.INTENT_GET_ROOMS)
        message.from = session.id
        message.to = session.id
        message.content = Response(INTENT_CORRECT, listToSend.toList()).toJson()
        return message
    }

    fun joinRoom(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val request = Gson().fromJson(msg.content, JoinRoomRequest::class.java)

        val findingRoom = SocketEndpoint.rooms.find {
            it.id == request.roomId &&
                    !(it.users!!.contains(session.id)) &&
                    it.code == request.code &&
                    ServerSocket.gameEngine.getConfigurations().space.configuration.maxPlayers > it.users!!.size
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

    fun getUsersInfo(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val request = Gson().fromJson(msg.content, GetUsersInfoRequest::class.java)

        val usersData = ArrayList<HumanPlayer>()

        request.userIds.forEach {
            val i = SocketEndpoint.usersInfo[it]

            if (i != null)
                usersData.add(
                    i
                )
        }

        val message = Message(Constants.INTENT_USERS_INFO)
        message.from = session.id
        message.to = session.id
        message.content = Response(INTENT_CORRECT, usersData).toJson()

        return message
    }

    fun uploadUserInfo(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {

        try {
            val request = JSON.gson.fromJson(msg.content, UploadUserInfoRequest::class.java)
            SocketEndpoint.usersInfo[session.id] = request.playerInfo

            println(JSON.gson.toJson(request.playerInfo.handDeck.items[0]))
        } catch (e: Exception) {
            println(e.message)
        }

        val message = Message(Constants.INTENT_UPLOAD_INFO)
        message.from = session.id
        message.to = session.id
        message.content = Response(INTENT_CORRECT, null).toJson()

        return message
    }

    //TODO
    fun updateRoomConfig() {}


}