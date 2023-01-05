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
}