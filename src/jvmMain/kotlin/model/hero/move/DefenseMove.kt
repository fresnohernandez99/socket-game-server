package model.hero.move

class DefenseMove(
    override val id: String,
    var percent: Int,
    val specific: Boolean,
    val specificType: ArrayList<String> = ArrayList()
) : AbstractMove(id)