package model.action

import model.deckAction.DeckAction
import model.hero.move.AbstractMove
import model.terrain.AbstractPiece

class Action(
    val playerId: String,
    val actionType: ActionType,

    // region PLAYER_TARGET
    val playerTargetId: String? = null,

    val deckAction: DeckAction? = null,
    // endregion PLAYER_TARGET

    // region PIECE_TARGET
    // FROM (SET PIECE ID FROM WHERE IS EMITTED THE ACTION)
    val pieceEmitterId: String? = null,

    val pieceTargetId: String? = null,

    val move: AbstractMove? = null,

    val moveToDeck: String? = null,
    // endregion PIECE_TARGET

    // SET_PIECE
    val piece: AbstractPiece? = null,

    )