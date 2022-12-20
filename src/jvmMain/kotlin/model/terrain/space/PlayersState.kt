package model.terrain.space

import model.player.AbstractPlayer
import model.deck.HeroDeck
import model.deck.GraveyardDeck
import model.deck.HandDeck

class PlayersState(
    val playerHeroDecks: ArrayList<HeroDeck>,
    val playersInfo: ArrayList<AbstractPlayer>,
    val playerHeroHandDecks: ArrayList<HandDeck>,
    val playerHeroGraveyardDecks: ArrayList<GraveyardDeck>
)