package socket.coder

import com.google.gson.Gson
import socket.model.Message
import javax.websocket.DecodeException
import javax.websocket.Decoder
import javax.websocket.EndpointConfig


class MessageDecoder : Decoder.Text<Message?> {
    @Throws(DecodeException::class)
    override fun decode(s: String?): Message {
        println("Undecode msg received: $s")

        if (s != null) {
            val firstBraced = s.indexOf('{')
            val lastBraced = s.lastIndexOf('}')

            println("f: $firstBraced")
            println("l: $lastBraced")
            println("size: ${s.length}")

            if (firstBraced != 0 && lastBraced != s.length + 1) {
                val godotMsg = s.substring(firstBraced, lastBraced + 1)
                try {
                    return gson.fromJson(
                        godotMsg,
                        Message::class.java
                    )
                } catch (e: Exception) {
                    println("Decoding error: $e")
                }
            }
        }
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