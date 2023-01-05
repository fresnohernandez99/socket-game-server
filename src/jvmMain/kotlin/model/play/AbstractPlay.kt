package model.play

abstract class AbstractPlay {
    abstract val playerId: String
    abstract var type: String

    companion object {
        const val PLAY_NAME = "abstract-play"
    }
}