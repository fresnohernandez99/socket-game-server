package model.hero.move

class DefenseMove(
    override val id: String,
    override val name: String,
    override val times: Int,
    var percent: Int,
    val specific: Boolean,
    val specificType: ArrayList<AttackType> = arrayListOf(AttackType.ALL)
) : AbstractMove()