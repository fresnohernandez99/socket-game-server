package model.hero.move

class DefenseMove(
    override val id: String,
    var percent: Int,
    val specific: Boolean,
    val specificTypes: ArrayList<String> = ArrayList(),
    override var type: String
) : AbstractMove() {
    companion object {
        const val MOVE_NAME = "defense"
    }
}