package model.hero.armor

class Boots(override val id: String, override val name: String, override val defensePoints: Float) : AbstractArmor() {
    override val type = ArmorParts.FOOTS

    override fun setEffect() {
    }
}