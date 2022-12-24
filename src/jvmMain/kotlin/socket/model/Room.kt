package socket.model

class Room(
    val id: String,
    val name: String,
    val code: String,
    val maxPlayer: Int = 2,
    var closed: Boolean = false
)