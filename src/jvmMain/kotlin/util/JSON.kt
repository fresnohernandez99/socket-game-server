package util

import com.google.gson.GsonBuilder
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import model.hero.Hero
import model.hero.move.*
import model.play.*
import model.player.AbstractPlayer
import model.player.HumanPlayer
import model.terrain.AbstractPiece

object JSON {

    private val runtimeTypeAdapterFactoryForPieces = RuntimeTypeAdapterFactory
        .of(AbstractPiece::class.java, "type")
        .registerSubtype(Hero::class.java, Hero.PIECE_NAME)

    private val runtimeTypeAdapterFactoryForPlays = RuntimeTypeAdapterFactory
        .of(AbstractPlay::class.java, "type")
        .registerSubtype(MovePieceFromSpacePlay::class.java, MovePieceFromSpacePlay.PLAY_NAME)
        .registerSubtype(MovePieceInDecksPlay::class.java, MovePieceInDecksPlay.PLAY_NAME)
        .registerSubtype(OverPiecePlay::class.java, OverPiecePlay.PLAY_NAME)
        .registerSubtype(SetInFieldPlay::class.java, SetInFieldPlay.PLAY_NAME)

    private val runtimeTypeAdapterFactoryForPlayers = RuntimeTypeAdapterFactory
        .of(AbstractPlayer::class.java, "type")
        .registerSubtype(HumanPlayer::class.java, HumanPlayer.PLAYER_NAME)

    private val runtimeTypeAdapterFactoryForMoves = RuntimeTypeAdapterFactory
        .of(AbstractMove::class.java, "type")
        .registerSubtype(BoostMove::class.java, BoostMove.MOVE_NAME)
        .registerSubtype(AttackMove::class.java, AttackMove.MOVE_NAME)
        .registerSubtype(DefenseMove::class.java, DefenseMove.MOVE_NAME)
        .registerSubtype(FieldModifierMove::class.java, FieldModifierMove.MOVE_NAME)
        .registerSubtype(HealMove::class.java, HealMove.MOVE_NAME)
        .registerSubtype(InstantHealMode::class.java, InstantHealMode.MOVE_NAME)
        .registerSubtype(AbstractIntervalMove::class.java, AbstractIntervalMove.MOVE_NAME)

    val gson = GsonBuilder()
        .registerTypeAdapterFactory(runtimeTypeAdapterFactoryForPieces)
        .registerTypeAdapterFactory(runtimeTypeAdapterFactoryForPlays)
        .registerTypeAdapterFactory(runtimeTypeAdapterFactoryForPlayers)
        .registerTypeAdapterFactory(runtimeTypeAdapterFactoryForMoves)
        .enableComplexMapKeySerialization()
        .create()

    fun setPieceType(piece: AbstractPiece) {
        val type = when (piece) {
            is Hero -> Hero.PIECE_NAME
            else -> AbstractPiece.PIECE_NAME
        }

        piece.type = type
    }

    fun setPlayType(play: AbstractPlay) {
        val type = when (play) {
            is MovePieceFromSpacePlay -> MovePieceFromSpacePlay.PLAY_NAME
            is MovePieceInDecksPlay -> MovePieceInDecksPlay.PLAY_NAME
            is OverPiecePlay -> OverPiecePlay.PLAY_NAME
            is SetInFieldPlay -> SetInFieldPlay.PLAY_NAME
            else -> AbstractPlay.PLAY_NAME
        }

        play.type = type
    }

    fun setPlayerType(player: AbstractPlayer) {
        val type = when (player) {
            is HumanPlayer -> HumanPlayer.PLAYER_NAME
            else -> AbstractPlayer.PLAYER_NAME
        }

        player.type = type
    }

    fun setMoveType(move: AbstractMove) {
        val type = when (move) {
            is BoostMove -> BoostMove.MOVE_NAME
            is AttackMove -> AttackMove.MOVE_NAME
            is DefenseMove -> DefenseMove.MOVE_NAME
            is FieldModifierMove -> FieldModifierMove.MOVE_NAME
            is HealMove -> HealMove.MOVE_NAME
            is InstantHealMode -> InstantHealMode.MOVE_NAME
            is AbstractIntervalMove -> AbstractIntervalMove.MOVE_NAME
            else -> AbstractMove.MOVE_NAME
        }

        move.type = type
    }
}