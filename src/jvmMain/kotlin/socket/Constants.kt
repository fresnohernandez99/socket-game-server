package socket

object Constants {
    const val INTENT_WITH_ERROR = "0"
    const val INTENT_CORRECT = "200"
    const val INTENT_START_LOOP = "202"
    const val INTENT_END_LOOP = "303"

    const val INTENT_CONNECTING = "1"
    const val INTENT_RE_CONNECTING = "2"

    const val INTENT_CREATE_ROOM = "3"
    const val INTENT_CLOSE_ROOM = "4"
    const val INTENT_CANCEL_ROOM = "5"
    const val INTENT_GET_ROOMS = "6"
    const val INTENT_JOIN_ROOM = "7"
    const val INTENT_USERS_INFO = "8"
    const val INTENT_UPLOAD_INFO = "9"
}