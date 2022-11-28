package model.terrain.space

import model.player.IPlayer
import model.terrain.deck.HeroDeck
import model.terrain.graveyard.GraveyardDeck
import model.terrain.hand.HandDeck

class PlayersState(
    val playerHeroDecks: ArrayList<HeroDeck>,
    val playersInfo: ArrayList<IPlayer>,
    val playerHeroHandDecks: ArrayList<HandDeck>,
    val playerHeroGraveyardDecks: ArrayList<GraveyardDeck>
)