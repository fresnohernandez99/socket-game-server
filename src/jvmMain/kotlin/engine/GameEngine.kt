package engine

import model.player.AbstractPlayer
import model.terrain.Terrain
import model.terrain.deck.HeroDeck
import model.terrain.hand.HandDeck
import model.terrain.rules.AbstractRule
import model.terrain.space.PlayersState
import model.terrain.space.Space
import model.terrain.space.SpaceConfiguration

class GameEngine {

    private lateinit var terrain: Terrain

    fun initConfiguration(
        configuration: SpaceConfiguration,
        initRules: ArrayList<AbstractRule> = ArrayList(),
    ) {
        terrain = Terrain(Space(configuration), initRules)
    }

    fun sendConfigurations(): Terrain {
        return terrain
    }

    /**
     * Battle
     * Receive all data from players
     */

    fun initBattle(playersList: ArrayList<AbstractPlayer>, playerHeroDecks: ArrayList<HeroDeck>): Terrain {
        return terrain.apply {
            val playerHeroHandDecks: ArrayList<HandDeck> = generateHands(playerHeroDecks)

            this.playersList = playersList
            this.space.setState(
                PlayersState(
                    playerHeroDecks = playerHeroDecks,
                    playersInfo = playersList,
                    playerHeroHandDecks = playerHeroHandDecks,
                    playerHeroGraveyardDecks = ArrayList()
                )
            )
        }
    }

    private fun generateHands(playerHeroDecks: ArrayList<HeroDeck>): ArrayList<HandDeck> {
        val spaceConfig = terrain.space.configuration

        val playerHeroHandDecks = ArrayList<HandDeck>()

        playerHeroDecks.forEachIndexed { index, heroDeck ->
            var cont = 0

            if (spaceConfig.limitInHand == -1) {
                playerHeroHandDecks.add(index, HandDeck().apply {
                    setNewItems(heroDeck.items)
                })
            } else {
                val handDeck = HandDeck()
                while (cont < heroDeck.items.size && cont < spaceConfig.limitInHand) {
                    handDeck.addItem(cont, heroDeck.items[cont])

                    // remove from actual deck
                    heroDeck.items.removeAt(cont)

                    cont++
                }
                playerHeroHandDecks.add(cont, handDeck)
            }
        }

        return playerHeroHandDecks
    }

    /**
     * Init round
     * Receive all players actions
     */

    fun initRound() {

    }

    /**
     * Calculate round
     * Make effects to all actions
     */

    fun calculateRound() {}

    /**
     * Evaluate round
     * Calculate defeat possibility
     */

    fun evaluateRound() {}

    /**
     * Round Result
     * Resume actual data into terrain
     */

    fun roundResult() {}
}