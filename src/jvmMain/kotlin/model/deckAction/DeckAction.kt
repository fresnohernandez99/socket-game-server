package model.deckAction

import model.deck.GraveyardDeck
import model.deck.HandDeck

class DeckAction(
    val cardNumberToAffect: Int,

    val deckTargetNameFromPop: String = HandDeck.DECK_NAME,
    val deckTargetNameToPop: String = GraveyardDeck.DECK_NAME,

    val effectShuffle: Boolean = true
)