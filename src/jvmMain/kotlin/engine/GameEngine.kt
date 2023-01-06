package engine

import model.deck.AbstractDeck
import model.hero.Hero
import model.hero.move.AttackMove
import model.play.*
import model.player.AbstractPlayer
import model.terrain.Terrain
import model.terrain.space.SpaceConfiguration
import util.JSON
import java.util.*


class GameEngine {

    private lateinit var terrain: Terrain
    private var wasBattleInitialized = false
    private var actualPlaysList = ArrayList<AbstractPlay>()
    private var playBy: Int = 0

    fun initConfiguration(
        terrain: Terrain
    ): GameEngine {
        this.terrain = terrain
        return this
    }

    fun getConfigurations(): SpaceConfiguration {
        return terrain.space.configuration
    }

    fun setConfigurations(configs: SpaceConfiguration) {
        terrain.space.configuration = configs
    }

    fun addPlayers(playersList: List<AbstractPlayer>) {
        if (!wasBattleInitialized)
            this.terrain.apply {
                this.playersList = playersList
            }
    }

    fun addPlays(list: List<AbstractPlay>): Int {
        actualPlaysList.addAll(list)
        playBy++
        return playBy
    }

    fun getPlaysForCalculate(): List<AbstractPlay> {
        return actualPlaysList
    }

    fun cleanPlays() {
        actualPlaysList = ArrayList()
        playBy = 0
    }

    fun getPlayers(): List<AbstractPlayer> {
        return terrain.playersList
    }

    fun getPlayerCount(): Int {
        return getPlayers().size
    }

    fun copy(): GameEngine {
        return GameEngine().apply {
            initConfiguration(this.terrain)
        }
    }

    /*
     * #################################################
     * #
     * # Initial methods for simple game
     * #
     * ##################################################
     */

    fun calculateActionsResult(pA: List<AbstractPlay>): List<AbstractPlay> {
        val playArray = ArrayList<AbstractPlay>()

        val onBack = ArrayList<AbstractPlay>()
        val auxList = ArrayList<AbstractPlay?>(Collections.nCopies(pA.size, null))

        if (getConfigurations().evaluateSpeed) {
            pA.forEach {
                if (it is OverPiecePlay) {
                    // TODO MEJORAR ALGORITMO
                    val speed = (terrain.spaceGrid.grid[it.positionFrom] as Hero).stats[4].value.toInt()
                    auxList.add(speed, it)
                } else {
                    onBack.add(it)
                }
            }

            auxList.forEach {
                if (it != null)
                    playArray.add(it)
            }

            playArray.addAll(onBack)
        } else {
            playArray.addAll(pA)
        }


        val playsResult = ArrayList<AbstractPlay>()
        playArray.forEach {
            JSON.setPlayType(it)
            when (it) {
                is SetInFieldPlay -> playsResult.add(setInField(it))
                is MovePieceInDecksPlay -> playsResult.add(movePieceFromInDecksPlay(it))
                is MovePieceFromSpacePlay -> playsResult.add(movePieceFromSpacePlay(it))
                is OverPiecePlay -> playsResult.add(overPiecePLay(it))
            }
        }

        return playsResult
    }

    private fun setInField(play: SetInFieldPlay): SetInFieldPlay {
        // TODO CHECK FIELD SIZE
        // TODO CHECK LIMIT IN FIELD

        // Remove piece from last position
        if (!play.piece.position.isNullOrEmpty() && play.piece.position != play.newPosition) {
            terrain.spaceGrid.grid.remove(play.piece.position)
        }

        // TODO CHECK NO ONE ON SETTING POSITION

        JSON.setPieceType(play.piece)

        play.piece.position = play.newPosition
        terrain.spaceGrid.grid[play.newPosition] = play.piece

        return play
    }

    private fun movePieceFromInDecksPlay(play: MovePieceInDecksPlay): MovePieceInDecksPlay {
        // Find player target
        val playerTarget = terrain.playersList.find { it.playerId == play.playerIdTarget }

        val deckToRemove = when (play.moveFrom) {
            AbstractDeck.PIECE_DECK -> playerTarget?.pieceDeck
            AbstractDeck.HAND_DECK -> playerTarget?.handDeck
            AbstractDeck.GRAVEYARD_DECK -> playerTarget?.graveyardDeck
            else -> playerTarget?.handDeck
        }

        val pieceRemoved = deckToRemove?.removeItem(play.pieceIdTarget)

        val deckToMove = when (play.moveTo) {
            AbstractDeck.PIECE_DECK -> playerTarget?.pieceDeck
            AbstractDeck.HAND_DECK -> playerTarget?.handDeck
            AbstractDeck.GRAVEYARD_DECK -> playerTarget?.graveyardDeck
            else -> playerTarget?.handDeck
        }

        deckToMove?.addItem(0, pieceRemoved)

        return play
    }

    private fun movePieceFromSpacePlay(play: MovePieceFromSpacePlay): MovePieceFromSpacePlay {
        // Find player target
        val playerTarget = terrain.playersList.find { it.playerId == play.playerIdTarget }

        // Get piece from grid
        val pieceRemoved = terrain.spaceGrid.grid[play.pieceIdTarget]

        val deckToMove = when (play.moveTo) {
            AbstractDeck.PIECE_DECK -> playerTarget?.pieceDeck
            AbstractDeck.HAND_DECK -> playerTarget?.handDeck
            AbstractDeck.GRAVEYARD_DECK -> playerTarget?.graveyardDeck
            else -> playerTarget?.handDeck
        }

        // Add piece to deck
        deckToMove?.addItem(0, pieceRemoved)

        //Remove piece from grid
        terrain.spaceGrid.grid.remove(play.pieceIdTarget)

        return play
    }

    private fun overPiecePLay(play: OverPiecePlay): OverPiecePlay {
        val piece = terrain.spaceGrid.grid[play.positionTo]

        JSON.setMoveType(play.move)

        //Calculate miss probability
        if (play.move is AttackMove && piece is Hero && getConfigurations().evaluateMiss) {
            val r = Math.random() * 100
            val isMiss = r <= piece.stats[1].value

            play.wasMiss = isMiss
        }

        return play
    }

}