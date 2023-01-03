package model.hero.equipment

class AbstractEquipment(
    val id: String,
    val name: String,
    val type: String,
    val info: String
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