package model.hero.move

class AbstractIntervalMove(
    override val id: String,
    val turns: Int,
    override var type: String
) : AbstractMove() {
    companion object {
        const val MOVE_NAME = "interval-heal"
    }
}