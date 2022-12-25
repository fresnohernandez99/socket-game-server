package socket.model

class Room(
    val id: String,
    var owner: String? = null,
    val name: String,
    val code: String,
    val maxPlayer: Int = 2,
    var closed: Boolean = false,
    val users: ArrayList<String> = ArrayList()
)