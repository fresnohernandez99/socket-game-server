package model.hero.move

class DefenseMove(
    override val id: String,
    override val name: String,
    var percent: Int,
    val specific: Boolean,
    val specificType: ArrayList<String>
) : AbstractMove()