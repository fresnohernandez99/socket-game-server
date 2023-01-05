package model.hero.move

class HealMove(
    override val id: String,
    val restoredPoints: Int,
    override var type: String
) : AbstractMove() {
    companion object {
        const val MOVE_NAME = "heal"
    }
}