package model.item

import model.hero.move.boosts.AbstractBoost
import model.effect.AbstractEffect

abstract class AbstractItem {
    abstract val id: String
    abstract val name: String

    abstract fun effect(): AbstractEffect

    abstract fun boost(): AbstractBoost
}