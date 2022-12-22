package model.player

import model.action.Action
import model.deck.GraveyardDeck
import model.deck.HandDeck
import model.deck.PieceDeck
import model.result.ActionToke

abstract class AbstractPlayer {
    abstract val name: String
    abstract val playerId: String
    abstract val lifePoints: Int
    abstract var lifePointsLose: Int
    abstract val pieceDeck: PieceDeck
    abstract val handDeck: HandDeck
    abstract val graveyardDeck: GraveyardDeck

    abstract fun applyAction(action: Action): ActionToke
}