package model.hero.move

class InstantHealMode(
    override val id: String,
    override val name: String,
    override val times: Int,
    val restoreLife: Boolean
) : AbstractMove()