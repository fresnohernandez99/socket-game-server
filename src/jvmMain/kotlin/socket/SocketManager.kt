package socket

import SocketEndpoint
import engine.GameEngine
import model.hero.Hero
import model.player.HumanPlayer
import model.terrain.Terrain
import model.terrain.space.DefeatCause
import model.terrain.space.Space
import model.terrain.space.SpaceConfiguration
import socket.Constants.INTENT_CORRECT
import socket.Constants.INTENT_WITH_ERROR
import socket.model.Message
import socket.model.Room
import socket.model.RoomWithConfigs
import socket.model.request.*
import socket.model.response.AbandonRoomResponse
import socket.model.response.Response
import socket.model.response.RoomsWithConfigResponse
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
        val room = JSON.gson.fromJson(msg.content, Room::class.java)
        room.owner = session.id
        room.users = ArrayList()
        room.users.add(session.id)

        val exists = SocketEndpoint.rooms.find { it.id == room.id }
        if (exists != null) SocketEndpoint.rooms.remove(exists)

        SocketEndpoint.rooms.add(room)

        // init configurations
        val serverConfigurations = Terrain(
            space = Space(
                SpaceConfiguration(
                    defeatCause = DefeatCause.SINGLE_PIECE_DEFEATED,
                    maxPlayers = 2
                )
            ),
            rules = ArrayList()
        )

        // init default room engine
        val gameEngine = GameEngine()
        gameEngine.initConfiguration(serverConfigurations)


        SocketEndpoint.roomTerrains[room.id] = gameEngine

        val message = Message(Constants.INTENT_CREATE_ROOM)
        message.from = session.id
        message.to = session.id
        message.content = Response(
            INTENT_CORRECT, RoomWithConfigs(
                room,
                SocketEndpoint.roomTerrains[room.id]!!.getConfigurations()
            )
        ).toJson()
        return message
    }

    fun closeRoom(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val request = JSON.gson.fromJson(msg.content, StartRoomRequest::class.java)

        val findingRoom = SocketEndpoint.rooms.find { it.id == request.roomId && it.owner == session.id }

        if (findingRoom != null) {
            findingRoom.closed = true

            val players = ArrayList<HumanPlayer>()
            findingRoom.users.forEach {
                players.add(
                    SocketEndpoint.usersInfo[it]!!
                )
            }

            SocketEndpoint.roomTerrains[findingRoom.id]!!.addPlayers(players)
        }

        val message = Message(Constants.INTENT_CLOSE_ROOM)
        message.from = session.id
        message.roomId = request.roomId
        message.content = Response(INTENT_CORRECT, null).toJson()
        return message
    }

    fun abandonRoom(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val request = JSON.gson.fromJson(msg.content, StartRoomRequest::class.java)

        val findingRoomIndex = SocketEndpoint.rooms.indexOfFirst { it.id == request.roomId }

        var roomId = ""

        if (findingRoomIndex != -1) {
            roomId = SocketEndpoint.rooms[findingRoomIndex].id
            if (SocketEndpoint.rooms[findingRoomIndex].owner == session.id) {
                SocketEndpoint.rooms.removeAt(findingRoomIndex)
                SocketEndpoint.roomTerrains.remove(request.roomId)
            } else {
                SocketEndpoint.rooms[findingRoomIndex].users.removeAll { it == session.id }
            }
        }

        val message = Message(Constants.INTENT_ABANDON_ROOM)
        message.from = session.id
        message.broadcast = true
        message.content = Response(INTENT_CORRECT, AbandonRoomResponse(session.id, roomId)).toJson()
        return message
    }

    fun getRooms(socketEndpoint: SocketEndpoint, session: Session): Message {
        val response = RoomsWithConfigResponse()

        SocketEndpoint.rooms.forEach {
            val configs = SocketEndpoint.roomTerrains[it.id]!!.getConfigurations()
            if (configs.maxPlayers > it.users.size) {
                response.rooms.add(
                    RoomWithConfigs(
                        it,
                        configs
                    )
                )
            }
        }

        val message = Message(Constants.INTENT_GET_ROOMS)
        message.from = session.id
        message.to = session.id
        message.content = Response(INTENT_CORRECT, response).toJson()
        return message
    }

    fun joinRoom(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val request = JSON.gson.fromJson(msg.content, JoinRoomRequest::class.java)

        val findingRoom = SocketEndpoint.rooms.find {
            it.id == request.roomId &&
                    !(it.users.contains(session.id)) &&
                    it.code == request.code &&
                    SocketEndpoint.roomTerrains[it.id]!!.getConfigurations().maxPlayers > it.users.size
        }


        val message = Message(Constants.INTENT_JOIN_ROOM)

        if (findingRoom != null) {
            findingRoom.users.add(session.id)
            message.from = session.id
            message.roomId = findingRoom.id
            message.content = Response(
                INTENT_CORRECT,
                RoomWithConfigs(findingRoom, SocketEndpoint.roomTerrains[findingRoom.id]!!.getConfigurations())
            ).toJson()
        } else {
            message.from = session.id
            message.to = session.id
            message.content = Response(INTENT_WITH_ERROR, null).toJson()
        }

        return message
    }

    fun getUsersInfo(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val request = JSON.gson.fromJson(msg.content, GetUsersInfoRequest::class.java)

        val usersData = ArrayList<HumanPlayer>()

        request.userIds.forEach {
            val i = SocketEndpoint.usersInfo[it]

            if (i != null)
                usersData.add(i)
        }

        val message = Message(Constants.INTENT_USERS_INFO)
        message.from = session.id
        message.to = session.id
        message.content = Response(INTENT_CORRECT, usersData).toJson()

        return message
    }

    fun uploadUserInfo(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val request = JSON.gson.fromJson(msg.content, UploadUserInfoRequest::class.java)
        SocketEndpoint.usersInfo[session.id] = request.playerInfo

        val message = Message(Constants.INTENT_UPLOAD_INFO)
        message.from = session.id
        message.to = session.id
        message.content = Response(INTENT_CORRECT, null).toJson()

        return message
    }

    fun getConfigurations(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val request = JSON.gson.fromJson(msg.content, GetConfigurationsRequest::class.java)

        val message = Message(Constants.INTENT_GET_CONFIGURATIONS)
        message.from = session.id
        message.to = session.id
        message.content =
            Response(INTENT_CORRECT, SocketEndpoint.roomTerrains[request.roomId]!!.getConfigurations()).toJson()

        return message
    }

    fun updateConfigurations(socketEndpoint: SocketEndpoint, session: Session, msg: Message): Message {
        val request = JSON.gson.fromJson(msg.content, UpdateConfigurationsRequest::class.java)
        val room = SocketEndpoint.rooms.find { it.id == request.roomId }

        val message = Message(Constants.INTENT_UPDATE_CONFIGURATIONS)
        message.from = session.id

        if (room != null && room.owner == session.id) {
            SocketEndpoint.roomTerrains[room.id]!!.setConfigurations(request.spaceConfiguration)
            message.roomId = room.id

            message.content =
                Response(INTENT_CORRECT, SocketEndpoint.roomTerrains[room.id]!!.getConfigurations()).toJson()
        } else {
            message.to = session.id

            message.content =
                Response(INTENT_WITH_ERROR, null).toJson()
        }


        return message
    }
}