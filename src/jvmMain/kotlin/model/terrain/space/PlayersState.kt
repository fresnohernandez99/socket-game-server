package model.terrain.space

import model.player.AbstractPlayer
import model.terrain.deck.HeroDeck
import model.terrain.graveyard.GraveyardDeck
import model.terrain.hand.HandDeck

class PlayersState(
    val playerHeroDecks: ArrayList<HeroDeck>,
    val playersInfo: ArrayList<AbstractPlayer>,
    val playerHeroHandDecks: ArrayList<HandDeck>,
    val playerHeroGraveyardDecks: ArrayList<GraveyardDeck>
)