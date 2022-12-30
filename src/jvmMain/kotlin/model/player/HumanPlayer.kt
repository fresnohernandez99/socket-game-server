package model.player

import model.action.Action
import model.deck.GraveyardDeck
import model.deck.HandDeck
import model.deck.PieceDeck
import model.hero.move.boosts.NegativeBoost
import model.hero.move.boosts.PositiveBoost
import model.result.ActionToke

class HumanPlayer(
    override val name: String,
    override val playerId: String,
    override val lifePoints: Int = 0,
    override var lifePointsLose: Int = 0,
    override val pieceDeck: PieceDeck = PieceDeck(),
    override val handDeck: HandDeck = HandDeck(),
    override val graveyardDeck: GraveyardDeck = GraveyardDeck()
) : AbstractPlayer() {
    override fun applyAction(action: Action): ActionToke {
        action.playerTarget_Boost_Toke?.let {
            if (it is PositiveBoost) {
                if (lifePointsLose < it.value) lifePointsLose = 0
                else if (lifePointsLose > 0) lifePointsLose -= it.value
            }

            if (it is NegativeBoost) {
                lifePointsLose += it.value
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
        PieceDeck.DECK_NAME -> pieceDeck
        HandDeck.DECK_NAME -> handDeck
        GraveyardDeck.DECK_NAME -> graveyardDeck
        else -> handDeck
    }

}