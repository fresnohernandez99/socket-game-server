package model.hero.move.boosts

abstract class AbstractBoost {
    abstract val attrToBoost: Int
    abstract val value: Int

    abstract fun getBoost(): AbstractBoost
}