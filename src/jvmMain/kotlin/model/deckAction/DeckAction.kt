package model.deckAction

import model.deck.AbstractDeck

class DeckAction(
    val cardNumberToAffect: Int,

    val deckTargetNameFromPop: String = AbstractDeck.HAND_DECK,
    val deckTargetNameToPop: String = AbstractDeck.GRAVEYARD_DECK,

    val effectShuffle: Boolean = true
)