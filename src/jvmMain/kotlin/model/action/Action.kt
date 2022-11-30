package model.action

import model.deckAction.DeckAction
import model.effect.AbstractEffect
import model.hero.move.AbstractMove
import model.hero.move.boosts.AbstractBoost
import model.terrain.AbstractPiece

class Action(
    val playerId: String,
    val actionType: ActionType,

    // PLAYER_TARGET
    val playerIdTarget: String? = null,

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

)