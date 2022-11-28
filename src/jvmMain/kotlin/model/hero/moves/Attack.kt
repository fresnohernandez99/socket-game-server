package model.hero.moves

open class Attack(
    override val id: String,
    override val name: String,
    override val times: Int,
    val damage: Double,
    val type: ArrayList<AttackType>
) : AbstractMove() {
    open fun effect(target: Any?) {}
}