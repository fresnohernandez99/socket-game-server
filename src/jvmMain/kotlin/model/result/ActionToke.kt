package model.result

import model.action.Action
import model.action.ActionType
import model.deckAction.DeckAction
import model.effect.AbstractEffect
import model.hero.move.AbstractMove
import model.hero.move.boosts.AbstractBoost
import model.terrain.AbstractPiece

class ActionToke(
    val playerIdFrom: String? = null,
    val actionType: ActionType,

    // PLAYER_TARGET
    val playerTarget_Effect_Toke: AbstractEffect? = null,
    val playerTarget_Boost_Toke: AbstractBoost? = null,

    // HERO_TARGET
    val heroIdTarget: String? = null,

    val move: AbstractMove? = null,
    val pieceTarget_Effect_Toke: AbstractEffect? = null,
    val pieceTarget_Boost_Toke: AbstractBoost? = null,

    // SET_PIECE
    val piece: AbstractPiece? = null,

    // DECK_TARGET
    val deckAction: DeckAction? = null
) {
    companion object {
        fun fromAction(action: Action): ActionToke {
            return ActionToke(
                playerIdFrom = action.playerId,
                actionType = action.actionType,
                playerTarget_Effect_Toke = action.playerTarget_Effect_Toke,
                playerTarget_Boost_Toke = action.playerTarget_Boost_Toke,
                heroIdTarget = action.heroIdTarget,
                move = action.move,
                pieceTarget_Effect_Toke = action.pieceTarget_Effect_Toke,
                pieceTarget_Boost_Toke = action.pieceTarget_Boost_Toke,
                piece = action.piece,
                deckAction = action.deckAction
            )
        }
    }
}
