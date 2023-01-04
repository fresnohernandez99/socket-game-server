package model.hero.move

import model.hero.move.AbstractMove

open class AbstractBoost(
    override val id: String,
    val attrToBoost: Int,
    val value: Int,
) : AbstractMove(id)