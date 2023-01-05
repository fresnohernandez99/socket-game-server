package model.hero.move

class FieldModifierMove(
    override val id: String,
    override var type: String
) : AbstractMove() {
    companion object {
        const val MOVE_NAME = "field"
    }
}