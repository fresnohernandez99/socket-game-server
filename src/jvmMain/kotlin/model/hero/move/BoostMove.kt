package model.hero.move

class BoostMove(
    override val id: String,
    val attrToBoost: Int,
    val value: Int,
    override var type: String,
) : AbstractMove() {
    companion object {
        const val MOVE_NAME = "boost"
    }
}