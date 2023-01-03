package model.effect

import model.hero.move.AbstractMove

class IntervalHeal(
    override val id: String,
    override val name: String,
    val restoredPoints: Boolean,
    val turns: Int
) : AbstractMove()