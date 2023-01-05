package model.hero.move

class AbstractIntervalMove(
    override val id: String,
    val turns: Int,
    override val type: String = MOVE_NAME
) : AbstractMove() {
    companion object {
        const val MOVE_NAME = "interval-heal"
    }
}