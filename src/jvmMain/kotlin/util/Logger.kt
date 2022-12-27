package util

import com.google.gson.Gson

object Logger {
    fun log(tag: String, msg: String, obj: Any? = null) {
        if (obj != null)
            println("$tag: $msg ${Gson().toJson(obj)}")
        else println("$tag: $msg}")
    }
}