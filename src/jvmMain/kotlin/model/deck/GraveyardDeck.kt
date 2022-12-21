package model.deck

class GraveyardDeck(
    override val name: String = DECK_NAME
) : AbstractDeck() {
    companion object {
        const val DECK_NAME = "graveyard-deck"
    }
}