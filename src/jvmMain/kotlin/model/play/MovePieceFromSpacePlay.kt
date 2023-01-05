package model.play

class MovePieceFromSpacePlay(
    override val playerId: String,
    override var type: String,
    val moveTo: String,
    val playerIdTarget: String,
    val pieceIdTarget: String
) : AbstractPlay() {
    companion object {
        const val PLAY_NAME = "move-piece-from-space"
    }
}