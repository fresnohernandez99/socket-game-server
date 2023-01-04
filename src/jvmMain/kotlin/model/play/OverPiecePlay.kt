package model.play

import model.hero.move.AbstractMove

class OverPiecePlay(
    override val playerId: String,
    override val type: String = PLAY_NAME,
    val positionFrom: String,
    val positionTo: String,
    val move: AbstractMove,
    var wasMiss: Boolean = false
) : AbstractPlay() {
    companion object {
        const val PLAY_NAME = "over-piece"
    }
}