package model.hero.move

import model.hero.move.AbstractMove

open class AbstractBoost(
    override val id: String,
    override val name: String,
    val attrToBoost: Int,
    val value: Int,
) : AbstractMove()