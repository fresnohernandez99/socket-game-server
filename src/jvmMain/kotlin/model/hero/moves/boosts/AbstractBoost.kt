package model.hero.moves.boosts

abstract class AbstractBoost {
    abstract val attrToBoost: String
    abstract val value: Float

    abstract fun getBoost(): AbstractBoost
}