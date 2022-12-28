package socket.model.response

import com.google.gson.Gson

class Response<T>(
    val code: String,
    val data: T? = null
) {
    fun toJson() = Gson().toJsonTree(this).asJsonObject
}