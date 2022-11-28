package model.hero.moves.boosts

class NegativeBoost(
    override var attrToBoost: String,
    override var value: Float
) : AbstractBoost() {

    override fun getBoost(): NegativeBoost {
        return this.apply {
            if (value > 0) value *= -1
        }
    }

}