package model.hero.moves.boosts

class PositiveBoost(
    override var attrToBoost: String,
    override var value: Float
) : AbstractBoost() {

    override fun getBoost(): PositiveBoost {
        return this
    }

}