package model.action

import model.hero.Hero
import model.hero.move.AbstractMove
import model.player.AbstractPlayer
import model.terrain.AbstractDeck

class Action (
    val actionType: ActionType,
    val heroTarget: Hero? = null,
    val playerTarget: AbstractPlayer? = null,
    val deckTarget: AbstractDeck? = null,
    val move: AbstractMove? = null
)