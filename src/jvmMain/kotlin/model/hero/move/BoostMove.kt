package model.hero.move

class BoostMove(
    override val id: String,
    val attrToBoost: Int,
    val value: Int,
    override val type: String = MOVE_NAME,
) : AbstractMove() {
    companion object {
        const val MOVE_NAME = "boost"
    }
}