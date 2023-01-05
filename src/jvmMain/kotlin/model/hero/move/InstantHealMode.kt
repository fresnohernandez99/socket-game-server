package model.hero.move

class InstantHealMode(
    override val id: String,
    val restoreLife: Boolean,
    val uses: Int,
    override val type: String = MOVE_NAME
) : AbstractMove() {
    companion object {
        const val MOVE_NAME = "instant-heal"
    }
}