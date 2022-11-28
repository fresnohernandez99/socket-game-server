package model.hero.armor

class Helmet(override val id: String, override val name: String, override val defensePoints: Float) : AbstractArmor() {
    override val type = ArmorParts.HEAD

    override fun setEffect() {
    }
}