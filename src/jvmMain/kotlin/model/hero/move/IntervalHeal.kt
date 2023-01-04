package model.hero.move

import model.hero.move.AbstractMove

class IntervalHeal(
    override val id: String,
    val restoredPoints: Boolean,
    val turns: Int
) : AbstractMove(id)