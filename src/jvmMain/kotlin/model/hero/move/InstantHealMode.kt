package model.hero.move

class InstantHealMode(
    override val id: String,
    val restoreLife: Boolean,
    val uses: Int
) : AbstractMove(id)