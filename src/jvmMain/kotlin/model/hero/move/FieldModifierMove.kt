package model.hero.move

class FieldModifierMove(
    override val id: String,
    override val type: String = MOVE_NAME
) : AbstractMove() {
    companion object {
        const val MOVE_NAME = "field"
    }
}