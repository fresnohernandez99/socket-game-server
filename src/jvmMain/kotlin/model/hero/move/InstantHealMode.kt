package model.hero.move

class InstantHealMode(
    override val id: String,
    val restoreLife: Boolean,
    val uses: Int,
    override var type: String
) : AbstractMove() {
    companion object {
        const val MOVE_NAME = "instant-heal"
    }
}