package model.item

import model.hero.move.boosts.AbstractBoost

abstract class AbstractItem {
    abstract val id: String
    abstract val name: String

    abstract fun boost(): AbstractBoost
}