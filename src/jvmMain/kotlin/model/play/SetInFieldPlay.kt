package model.play

import model.terrain.AbstractPiece

class SetInFieldPlay(
    override val playerId: String,
    override val type: String = PLAY_NAME,
    val piece: AbstractPiece,
    val newPosition: String
) : AbstractPlay() {
    companion object {
        const val PLAY_NAME = "set-in-field"
    }
}