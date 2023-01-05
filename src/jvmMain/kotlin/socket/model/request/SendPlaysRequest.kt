package socket.model.request

import model.play.AbstractPlay

class SendPlaysRequest(
    val roomId: String,
    var plays: List<AbstractPlay>
)