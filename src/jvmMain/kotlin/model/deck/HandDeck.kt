package model.deck

class HandDeck(
    override val name: String = DECK_NAME
) : AbstractDeck() {
    companion object {
        const val DECK_NAME = "hand-deck"
    }
}