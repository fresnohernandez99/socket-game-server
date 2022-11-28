package model.hero.armor

class Gloves(override val id: String, override val name: String, override val defensePoints: Float) : AbstractArmor() {
    override val type = ArmorParts.HANDS

    override fun setEffect() {
    }
}