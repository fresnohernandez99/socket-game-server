package model.hero.move

class HealMove(
    override val id: String,
    override val name: String,
    override val times: Int,
    val restoredPoints: Int
) : AbstractMove()