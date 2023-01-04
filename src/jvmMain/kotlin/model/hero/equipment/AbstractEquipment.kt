package model.hero.equipment

open class AbstractEquipment(
    open val id: String,
    open val name: String,
    open val type: String,
    open val info: String
) {
    companion object {
        const val HEAD = "head"
        const val HANDS = "hands"
        const val TORSO = "torso"
        const val FOOTS = "foots"
        const val WEAPON = "weapon"
        const val EXTRA = "extra"
    }
}