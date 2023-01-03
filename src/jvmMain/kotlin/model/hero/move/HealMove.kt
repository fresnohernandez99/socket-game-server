package model.hero.move

class HealMove(
    override val id: String,
    override val name: String,
    val restoredPoints: Int
) : AbstractMove()