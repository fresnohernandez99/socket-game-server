package model.hero.move

open class AttackMove(
    override val id: String,
    override val name: String,
    override val times: Int,
    val damage: Int,
    val type: ArrayList<AttackType>
) : AbstractMove() {
    open fun effect(target: Any?) {}
}