package socket

import com.google.gson.Gson
import javax.websocket.DecodeException
import javax.websocket.Decoder
import javax.websocket.EndpointConfig


class MessageDecoder : Decoder.Text<Message?> {
    @Throws(DecodeException::class)
    override fun decode(s: String?): Message {
        return gson.fromJson(s, Message::class.java)
    }

    override fun willDecode(s: String?): Boolean {
        return s != null
    }

    override fun init(endpointConfig: EndpointConfig?) {
        // Custom initialization logic
    }

    override fun destroy() {
        // Close resources
    }

    companion object {
        private val gson: Gson = Gson()
    }
}