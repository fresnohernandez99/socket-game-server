package model.hero.move

class InstantHealMode(
    override val id: String,
    override val name: String,
    val restoreLife: Boolean
) : AbstractMove()