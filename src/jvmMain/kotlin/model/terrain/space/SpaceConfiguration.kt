package model.terrain.space

class SpaceConfiguration(
    var limitInHand: Int = -1,
    var limitInDeck: Int = -1,
    var limitInGraveyard: Int = -1,
    var limitCardToDraw: Int = 1,
    var maxCardToDraw: Int = -1,
    var maxPlayers: Int = -1,
    var maxAttackNumbForPlayer: Int = 1,
    var minPlayers: Int = 2,
    var defeatCause: DefeatCause
)