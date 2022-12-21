package model.deck

class PieceDeck(
    override val name: String = DECK_NAME
) : AbstractDeck() {
    companion object {
        const val DECK_NAME = "piece-deck"
    }
}