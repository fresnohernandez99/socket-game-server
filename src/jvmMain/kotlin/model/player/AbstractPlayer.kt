package model.player

import model.action.Action
import model.deck.AbstractDeck
import model.result.ActionToke

abstract class AbstractPlayer {
    abstract val name: String
    abstract val playerId: String
    abstract val lifePoints: Int
    abstract var lifePointsLose: Int
    abstract var pieceDeck: AbstractDeck
    abstract var handDeck: AbstractDeck
    abstract var graveyardDeck: AbstractDeck

    abstract fun applyAction(action: Action): ActionToke
}