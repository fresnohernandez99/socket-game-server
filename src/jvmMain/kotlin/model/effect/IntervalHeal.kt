package model.effect

import model.hero.move.AbstractMove

class IntervalHeal(
    override val id: String,
    override val name: String,
    override val times: Int,
    val restoredPoints: Boolean,
    val turns: Int
) : AbstractMove()