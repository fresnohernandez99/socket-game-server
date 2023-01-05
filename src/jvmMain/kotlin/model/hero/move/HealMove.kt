package model.hero.move

class HealMove(
    override val id: String,
    val restoredPoints: Int,
    override val type: String = MOVE_NAME
) : AbstractMove() {
    companion object {
        const val MOVE_NAME = "heal"
    }
}