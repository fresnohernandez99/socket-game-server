package socket.model.response

import com.google.gson.Gson

class Response<T>(
    val code: Int,
    val msg: String? = null,
    val data: T? = null
) {
    fun toJson() = Gson().toJsonTree(this).asJsonObject
}