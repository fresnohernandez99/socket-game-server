package model.hero.stat

class AbstractStat(
    val name: String,
    var value: Float
) {
    companion object {
        const val STAT_DEFENSE = "defense"
        const val STAT_EVASION = "evasion"
        const val STAT_INTELLECT = "intellect"
        const val STAT_MAGIC = "magic"
        const val STAT_SPEED = "speed"
        const val STAT_STRENGTH = "strength"
    }
}