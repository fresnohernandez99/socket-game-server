package model.hero.move

class HealMove(
    override val id: String,
    val restoredPoints: Int
) : AbstractMove(id)