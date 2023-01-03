package model.hero.move

class InstantHealMode(
    override val id: String,
    val restoreLife: Boolean
) : AbstractMove()