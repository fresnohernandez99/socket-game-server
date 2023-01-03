package model.result

import model.action.Action
import model.action.ActionType
import model.deckAction.DeckAction
import model.hero.move.AbstractMove
import model.terrain.AbstractPiece

class ActionToke(
    var wasError: Boolean = false,
    val playerId: String? = null,
    val actionType: ActionType,

    // region PLAYER_TARGET
    val playerTargetId: String? = null,

    // endregion PLAYER_TARGET

    // region PIECE_TARGET
    // FROM (SET PIECE ID FROM WHERE IS EMITTED THE ACTION)
    val pieceEmitterId: String? = null,

    var wasMiss: Boolean = false,
    val pieceIdTarget: String? = null,

    val move: AbstractMove? = null,

    val moveToDeck: String? = null,
    // endregion PIECE_TARGET

    // SET_PIECE
    val piece: AbstractPiece? = null,

    // DECK_TARGET
    // Debe tener un player target
    val deckAction: DeckAction? = null,

) {
    companion object {
        fun fromAction(action: Action): ActionToke {
            return ActionToke(
                playerId = action.playerId,
                actionType = action.actionType,
                playerTargetId = action.playerTargetId,
                pieceIdTarget = action.pieceTargetId,
                move = action.move,
                piece = action.piece,
                deckAction = action.deckAction,
                pieceEmitterId = action.pieceEmitterId,
                moveToDeck = action.moveToDeck
            )
        }
    }
}
