package socket

import com.google.gson.Gson
import javax.websocket.EncodeException
import javax.websocket.Encoder
import javax.websocket.EndpointConfig


class MessageEncoder : Encoder.Text<Message?> {
    @Throws(EncodeException::class)
    override fun encode(message: Message?): String {
        return gson.toJson(message)
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