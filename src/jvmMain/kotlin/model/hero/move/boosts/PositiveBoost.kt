package model.hero.move.boosts

class PositiveBoost(
    override var attrToBoost: Int,
    override var value: Int
) : AbstractBoost() {

    override fun getBoost(): PositiveBoost {
        return this
    }

}