package model.player

abstract class AbstractPlayer {
    abstract val name: String
    abstract val id: String
    abstract val playerType: PlayerType
}