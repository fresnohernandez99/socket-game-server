package model.deckAction

class DeckAction(
    val cardNumberToAffect: Int,

    val deckTargetNameFromPop: String? = null,
    val deckTargetNameToPop: String? = null,

    val deckTargetNameFromPush: String? = null,
    val deckTargetNameToPush: String? = null
)