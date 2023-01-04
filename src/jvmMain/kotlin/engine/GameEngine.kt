package engine

import model.action.Action
import model.action.ActionType.*
import model.deck.AbstractDeck
import model.hero.Hero
import model.hero.move.AttackMove
import model.play.*
import model.player.AbstractPlayer
import model.result.ActionToke
import model.result.RoundResult
import model.terrain.Terrain
import model.terrain.space.DefeatCause
import model.terrain.space.SpaceConfiguration

class GameEngine {

    private lateinit var terrain: Terrain
    private var wasBattleInitialized = false

    fun initConfiguration(
        terrain: Terrain
    ): GameEngine {
        if (!this::terrain.isInitialized) this.terrain = terrain
        return this
    }

    fun getConfigurations(): SpaceConfiguration {
        return terrain.space.configuration
    }

    fun addPlayers(playersList: ArrayList<AbstractPlayer>) {
        if (!wasBattleInitialized)
            this.terrain.apply {
                this.playersList = playersList
            }
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

    fun calculateActionsResult(playArray: List<AbstractPlay>): ArrayList<AbstractPlay> {
        val playsResult = ArrayList<AbstractPlay>()
        playArray.forEach {
            when (it) {
                is SetInFieldPlay -> playsResult.add(setInField(it))
                is MovePieceInDecksPlay -> playsResult.add(movePieceFromInDecksPlay(it))
                is MovePieceFromSpacePlay -> playsResult.add(movePieceFromSpacePlay(it))
                is OverPiecePlay -> playsResult.add(overPiecePLay(it))
            }
        }

        return playsResult
    }

    private fun setInField(play: SetInFieldPlay): AbstractPlay {
        // TODO CHECK FIELD SIZE
        // TODO CHECK LIMIT IN FIELD

        // Remove piece from last position
        if (!play.piece.position.isNullOrEmpty() && play.piece.position != play.newPosition) {
            terrain.spaceGrid.grid.remove(play.piece.position)
        }

        // TODO CHECK NO ONE ON SETTING POSITION

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

        //Calculate miss probability
        if (play.move is AttackMove && piece is Hero && getConfigurations().evaluateMiss) {
            val r = Math.random() * 100
            val isMiss = r <= piece.stats[1].value

            play.wasMiss = isMiss
        }

        return play
    }
}