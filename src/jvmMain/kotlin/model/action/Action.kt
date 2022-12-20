package model.action

import model.deckAction.DeckAction
import model.effect.AbstractEffect
import model.hero.move.AbstractMove
import model.hero.move.boosts.AbstractBoost
import model.terrain.AbstractPiece

class Action(
    val playerId: String,
    val actionType: ActionType,

    // region PLAYER_TARGET
    val playerTargetId: String? = null,

    val playerTarget_Effect_Toke: AbstractEffect? = null,
    val playerTarget_Boost_Toke: AbstractBoost? = null,
    // endregion PLAYER_TARGET

    // region PIECE_TARGET
    // FROM (SET PIECE ID FROM WHERE IS EMITTED THE ACTION)
    val pieceEmitterId: String? = null,

    val pieceTargetId: String? = null,

    val move: AbstractMove? = null,
    val pieceTarget_Effect_Toke: AbstractEffect? = null,
    val pieceTarget_Boost_Toke: AbstractBoost? = null,
    // endregion PIECE_TARGET

    // SET_PIECE
    val piece: AbstractPiece? = null,

    // DECK_TARGET
    val deckAction: DeckAction? = null,

)