package model.hero.move

class AttackMove(
    override val id: String,
    val damage: Int,
    val types: ArrayList<String>,
    override var type: String
) : AbstractMove() {
    companion object {
        const val MOVE_NAME = "attack"
    }
}