package model.player

abstract class AbstractPlayer {
    abstract val name: String
    abstract val playerId: String
    abstract val playerType: PlayerType
}