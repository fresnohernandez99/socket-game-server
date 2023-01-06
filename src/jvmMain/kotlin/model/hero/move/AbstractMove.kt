package model.hero.move

abstract class AbstractMove {
    abstract val id: String
    abstract var type: String

    companion object {
        const val MOVE_NAME = "abstract-move"
    }
}