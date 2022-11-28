package model.hero.move

class Defence(
    override val id: String,
    override val name: String,
    override val times: Int,
    val damageTaken: Double,
    val specific: Boolean,
    val specificType: ArrayList<AttackType> = arrayListOf(AttackType.ALL)
) : AbstractMove()