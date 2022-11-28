package model.hero.moves

class InstantHeal(
    override val id: String,
    override val name: String,
    override val times: Int,
    val restoredPoints: Boolean
) : AbstractMove()