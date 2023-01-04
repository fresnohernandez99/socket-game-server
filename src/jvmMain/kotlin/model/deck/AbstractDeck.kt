package model.deck

import model.terrain.AbstractPiece

open class AbstractDeck(
    open val name: String = "",
    open val items: ArrayList<AbstractPiece> = ArrayList()
) {
    fun setNewItems(itemsForDeck: ArrayList<AbstractPiece>): ArrayList<AbstractPiece> {
        clearDeck()
        items.addAll(itemsForDeck)
        return items
    }

    fun addItem(position: Int, piece: AbstractPiece?): ArrayList<AbstractPiece> {
        if (piece != null) {
            if (position < items.size) items.add(position, piece)
            else items.add(piece)
        }
        return items
    }

    fun addItems(pieces: ArrayList<AbstractPiece>): ArrayList<AbstractPiece> {
        items.addAll(pieces)
        return items
    }

    fun removeItemAt(position: Int) {
        if (position < items.size) items.removeAt(position)
    }

    fun removeItem(pieceId: String): AbstractPiece? {
        val piece = items.find { it.id == pieceId }
        items.remove(piece)
        return piece
    }

    fun getDeck() = items

    fun clearDeck() {
        items.clear()
    }

    fun mergeTwo(itemsDeck1: ArrayList<AbstractPiece>, itemsDeck2: ArrayList<AbstractPiece>): ArrayList<AbstractPiece> {
        clearDeck()
        items.addAll(itemsDeck1)
        items.addAll(itemsDeck2)
        items.shuffle()
        return items
    }

    fun shuffle() = items.shuffle()

    fun getAmountOfPieces(amount: Int): ArrayList<AbstractPiece> {
        val extracted = ArrayList<AbstractPiece>()
        try {
            for (i in 0 until amount) {
                extracted.add(items[0])
                items.removeAt(0)
            }
        } catch (e: Exception) {
        }
        return extracted
    }

    fun amount() = items.size

    companion object {
        const val HAND_DECK = "hand-deck"
        const val GRAVEYARD_DECK = "graveyard-deck"
        const val PIECE_DECK = "piece-deck"
    }
}