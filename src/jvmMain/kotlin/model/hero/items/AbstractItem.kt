package model.hero.items

import model.hero.moves.boosts.AbstractBoost
import model.hero.effects.AbstractEffect

abstract class AbstractItem {
    abstract val id: String
    abstract val name: String

    abstract fun effect(): AbstractEffect

    abstract fun boost(): AbstractBoost
}