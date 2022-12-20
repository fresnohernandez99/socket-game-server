package model.hero.move.boosts

class NegativeBoost(
    override var attrToBoost: Int,
    override var value: Int
) : AbstractBoost() {

    override fun getBoost(): NegativeBoost {
        return this.apply {
            if (value > 0) value *= -1
        }
    }

}