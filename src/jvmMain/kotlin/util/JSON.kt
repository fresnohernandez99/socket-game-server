package util

import com.google.gson.GsonBuilder
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import model.hero.Hero
import model.terrain.AbstractPiece

object JSON {

    private val runtimeTypeAdapterFactoryForPieces = RuntimeTypeAdapterFactory
        .of(AbstractPiece::class.java, "type")
        .registerSubtype(Hero::class.java, "hero")

    val gson = GsonBuilder()
        .registerTypeAdapterFactory(runtimeTypeAdapterFactoryForPieces)
        .enableComplexMapKeySerialization()
        .create()
}