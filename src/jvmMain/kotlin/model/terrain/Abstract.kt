package model.terrain

abstract class Abstract {
    abstract val name: String
    open val items: ArrayList<AbstractPiece> = ArrayList()

    fun setNewItems(itemsForDeck: ArrayList<AbstractPiece>): ArrayList<AbstractPiece> {
        clearDeck()
        items.addAll(itemsForDeck)
        return items
    }

    fun addItem(position: Int, piece: AbstractPiece): ArrayList<AbstractPiece> {
        if (position < items.size) items.removeAt(position)
        return items
    }

    fun removeItem(position: Int): ArrayList<AbstractPiece> {
        if (position < items.size) items.removeAt(position)
        return items
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
}