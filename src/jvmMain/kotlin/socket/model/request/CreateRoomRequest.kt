package socket.model.request

class CreateRoomRequest(
    val hostId: String,
    val name: String,
    val code: String,
    val maxPlayer: Int = 2
)