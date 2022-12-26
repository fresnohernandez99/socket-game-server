package socket.model

import model.terrain.space.SpaceConfiguration

class Room(
    val id: String,
    var owner: String? = null,
    val name: String,
    val code: String,
    var closed: Boolean = false,
    var configuration: SpaceConfiguration,
    var users: ArrayList<String>? = null
)