package model.hero.moves

abstract class AbstractMove {
    abstract val id: String
    abstract val name: String
    abstract val times: Int
}