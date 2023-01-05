package model.hero.move

class DefenseMove(
    override val id: String,
    var percent: Int,
    val specific: Boolean,
    val specificTypes: ArrayList<String> = ArrayList(),
    override val type: String = MOVE_NAME
) : AbstractMove() {
    companion object {
        const val MOVE_NAME = "defense"
    }
}