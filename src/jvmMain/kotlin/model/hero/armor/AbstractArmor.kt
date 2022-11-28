package model.hero.armor

abstract class AbstractArmor {
    abstract val id: String
    abstract val name: String
    abstract val type: ArmorParts
    abstract val defensePoints: Float

    abstract fun setEffect()
}