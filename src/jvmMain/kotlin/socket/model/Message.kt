package socket.model

import com.google.gson.JsonObject

class Message(
    var endpoint: String,
    var from: String? = null,
    var to: String? = null,
    var content: JsonObject? = null
)