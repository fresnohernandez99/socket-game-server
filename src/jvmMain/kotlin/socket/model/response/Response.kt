package socket.model.response

class Response<T>(
    val code: Int,
    val msg: String? = null,
    val data: T? = null
)