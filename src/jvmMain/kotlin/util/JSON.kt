package util

import com.google.gson.GsonBuilder
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import model.hero.Hero
import model.play.*
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

    val gson = GsonBuilder()
        .registerTypeAdapterFactory(runtimeTypeAdapterFactoryForPieces)
        .registerTypeAdapterFactory(runtimeTypeAdapterFactoryForPlays)
        .enableComplexMapKeySerialization()
        .create()
}