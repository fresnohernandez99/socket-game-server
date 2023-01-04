package socket.model

import engine.GameEngine

class Room(
    val id: String,
    var owner: String? = null,
    val name: String,
    var code: String,
    var closed: Boolean = false,
    var users: ArrayList<String> = ArrayList()
)