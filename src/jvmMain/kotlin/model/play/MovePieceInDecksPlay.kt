package model.play

import model.hero.move.AbstractMove

class MovePieceInDecksPlay(
    override val playerId: String,
    override var type: String,
    val moveFrom: String,
    val moveTo: String,
    val playerIdTarget: String,
    val pieceIdTarget: String
) : AbstractPlay() {
    companion object {
        const val PLAY_NAME = "move-piece-decks"
    }
}