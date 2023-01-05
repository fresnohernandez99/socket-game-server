package model.player

import model.action.Action
import model.deck.AbstractDeck
import model.hero.move.BoostMove
import model.hero.stat.AbstractStat
import model.result.ActionToke

class HumanPlayer(
    override val name: String,
    override val playerId: String,
    override val lifePoints: Int = 0,
    override var lifePointsLose: Int = 0,
    var stats: ArrayList<AbstractStat> = ArrayList(),
    override var pieceDeck: AbstractDeck = AbstractDeck(),
    override var handDeck: AbstractDeck = AbstractDeck(),
    override var graveyardDeck: AbstractDeck = AbstractDeck(),
    override var type: String
) : AbstractPlayer() {
    override fun applyAction(action: Action): ActionToke {
        action.move?.let {
            if (it is BoostMove) {
                stats[it.attrToBoost].value += it.value
            }
        }

        action.deckAction?.let {
            val deckFromPop = getDeckByName(it.deckTargetNameFromPop)
            val deckToPop = getDeckByName(it.deckTargetNameToPop)

            val tokeCards = deckFromPop.getAmountOfPieces(it.cardNumberToAffect)

            deckToPop.addItems(tokeCards)

            if (it.effectShuffle) deckFromPop.shuffle()
        }

        return ActionToke.fromAction(action).apply {
            wasError = false
        }
    }

    private fun getDeckByName(name: String) = when (name) {
        AbstractDeck.PIECE_DECK -> pieceDeck
        AbstractDeck.HAND_DECK -> handDeck
        AbstractDeck.GRAVEYARD_DECK -> graveyardDeck
        else -> handDeck
    }

    companion object {
        const val PLAYER_NAME = "human"
    }
}