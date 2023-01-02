package model.terrain.space

class SpaceConfiguration(
    var limitInField: Int = 1,
    var limitInHand: Int = -1,
    var limitInDeck: Int = -1,
    var limitInGraveyard: Int = -1,
    var limitCardToDraw: Int = -1,
    var maxCardToDraw: Int = -1,
    var maxPlayers: Int = 2,
    var maxActionsPerTurn: Int = 1,
    var maxTurns: Int = -1,
    var maxLevelAllowedInRoom: Int = -1,
    var minLevelAllowedInRoom: Int = -1,
    var minPlayers: Int = 2,
    var fieldSize: Int = -1,
    var defeatCause: Int,
    var generateRandomHands: Boolean = false
)

