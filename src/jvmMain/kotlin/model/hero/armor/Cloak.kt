package model.hero.armor

class Cloak(override val id: String, override val name: String, override val defensePoints: Float) : AbstractArmor() {
    override val type = ArmorParts.EXTRA

    override fun setEffect() {
    }
}