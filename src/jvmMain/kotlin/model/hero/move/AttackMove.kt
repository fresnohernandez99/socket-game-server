package model.hero.move

open class AttackMove(
    override val id: String,
    val damage: Int,
    val type: ArrayList<String>
) : AbstractMove() {
    open fun effect(target: Any?) {}
}