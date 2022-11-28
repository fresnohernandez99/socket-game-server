package model.hero.armor

class Chest(override val id: String, override val name: String, override val defensePoints: Float) : AbstractArmor() {
    override val type = ArmorParts.TORSO

    override fun setEffect() {
    }
}