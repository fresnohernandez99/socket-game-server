package engine

import model.action.Action
import model.action.ActionType.*
import model.deck.GraveyardDeck
import model.deck.HandDeck
import model.deck.PieceDeck
import model.hero.Hero
import model.player.AbstractPlayer
import model.result.ActionToke
import model.result.RoundResult
import model.terrain.Terrain
import model.terrain.space.DefeatCause

class GameEngine {

    private lateinit var terrain: Terrain
    private var wasBattleInitialized = false

    /**
     * Iniciado por el servidor una única vez por tipo de juego.
     * Objeto global.
     */
    fun initConfiguration(
        terrain: Terrain
    ): GameEngine {
        if (!this::terrain.isInitialized) this.terrain = terrain
        return this
    }

    /**
     * Método por partida.
     * Devuelve el formato de juego.
     * Usado para actualizar el estado inicial de los clientes.
     */
    fun getConfigurations(): Terrain {
        return terrain
    }

    fun addPlayers(playersList: ArrayList<AbstractPlayer>) {
        if (!wasBattleInitialized)
            this.terrain.apply {
                this.playersList = playersList
            }
    }

    /**
     * Battle
     * Receive all data from players
     * Se activa al tener todos los players listos. (SOCKET)
     */
    fun initBattle(): Terrain {
        wasBattleInitialized = true
        return terrain.apply {

            if (this.space.configuration.generateRandomHands) {
                this.playersList.forEach {
                    // TODO generate random hands
                }
            }

        }
    }

    /**
     * Luego de iniciado el combate.
     * Primera acción recibida por los clientes. (SOCKET)
     */
    private fun generateHands(players: ArrayList<AbstractPlayer>) {

    }

    /**
     * Init round
     * Receive all players actions
     * La posicion del arreglo recibido dice de que player es la acción. (MANEJAR ORDEN EN EL SOCKET)
     * En cada ronda al terminar de recibir las acciones.(SOCKET-MOTOR)
     */
    fun receivePlayerActionsAndRun(playerActions: ArrayList<Action>): RoundResult {
        val actionsToke = ArrayList<ActionToke>()

        actionsToke.addAll(applyActions(playerActions))

        //mover piezas a deck correspondientes
        terrain.spaceGrid.position.forEach { piece ->
            if (piece is Hero) {
                if (piece.lifePoints <= piece.lifePointsLose) {
                    // conseguir player duenno del heroe
                    // mover heroe al cementerio
                    // informar de movimiento
                    actionsToke.add(
                        ActionToke(
                            actionType = PIECE_TARGET,
                            moveToDeck = GraveyardDeck.DECK_NAME,
                            pieceIdTarget = piece.id,
                            playerId = piece.playerId
                        )
                    )

                    if (DefeatCause.SINGLE_PIECE_DEFEATED == terrain.space.configuration.defeatCause) {
                        if (terrain.loserPlayers.find { it.playerId == piece.playerId } == null) {
                            terrain.playersList.find { it.playerId == piece.playerId }?.let {
                                terrain.loserPlayers.add(it)
                            }
                        }
                    }
                }
            }
        }

        when (terrain.space.configuration.defeatCause) {
            DefeatCause.LIFE_POINTS -> {
                terrain.playersList.forEach {
                    if (it.lifePoints <= it.lifePointsLose) {
                        terrain.loserPlayers.add(it)
                    }
                }
            }

            DefeatCause.NO_MORE_PIECES_ON_PIECE_DECK -> {
                terrain.playersList.forEach {
                    if (it.pieceDeck.amount() == 0) terrain.loserPlayers.add(it)
                }
            }

            DefeatCause.NO_MORE_PIECES_ON_THE_FIELD -> {
                val playersWithPiecesTemp = ArrayList<String>()

                terrain.spaceGrid.position.forEach {
                    playersWithPiecesTemp.add(it.playerId)
                }

                playersWithPiecesTemp.sort()

                val playersWithPieces = ArrayList<String>()
                var actualId = ""
                playersWithPiecesTemp.forEach {
                    if (actualId != it) {
                        playersWithPieces.add(it)
                        actualId = it
                    }
                }

                terrain.playersList.forEach { player ->
                    val exist = playersWithPieces.find { it == player.playerId }
                    if (exist == null) terrain.loserPlayers.add(player)
                }
            }

            else -> {}
        }

        return RoundResult(
            playersReceive = actionsToke, loserPlayers = terrain.loserPlayers
        )
    }

    /**
     * Aplica cada accion recibida de cada jugador
     * Se guarda cada uno de los resultados de las acciones
     * Se devuelve un arreglo de resultados para cada jugador
     */
    private fun applyActions(actions: ArrayList<Action>): ArrayList<ActionToke> {
        val playerActionsResults = ArrayList<ActionToke>()

        actions.forEach {
            playerActionsResults.add(
                when (it.actionType) {
                    SET_PIECE -> setPiece(it)
                    PIECE_TARGET -> actionOverPiece(it)
                    PLAYER_TARGET -> actionOverPlayer(it)
                    PASS -> actionPass(it)
                    RUN -> actionRun(it)
                    else -> actionPass(it)
                }
            )
        }
        return playerActionsResults
    }

    /**
     * Poner Piece en el grid del espacio
     */
    private fun setPiece(action: Action): ActionToke {
        // TODO CHECK FIELD SIZE
        // TODO CHECK LIMIT IN FIELD

        action.piece!!.playerId = action.playerId

        // TODO CHECK NO ONE ON SETTING POSITION

        terrain.spaceGrid.position.add(action.piece)

        return ActionToke.fromAction(action)
    }

    private fun actionOverPiece(action: Action): ActionToke {
        val piece = terrain.spaceGrid.position.find { it.id == action.pieceTargetId }
        val playerTarget = terrain.playersList.find { it.playerId == action.playerTargetId }

        if (action.moveToDeck != null) {
            val deckToMove = when (action.moveToDeck) {
                PieceDeck.DECK_NAME -> playerTarget?.pieceDeck
                HandDeck.DECK_NAME -> playerTarget?.handDeck
                GraveyardDeck.DECK_NAME -> playerTarget?.graveyardDeck
                else -> playerTarget?.handDeck
            }

            deckToMove?.addItem(0, piece)

            return ActionToke.fromAction(action)
        }

        return piece?.applyAction(action) ?: ActionToke.fromAction(action).apply {
            wasError = true
        }
    }

    private fun actionOverPlayer(action: Action): ActionToke {
        val playerTarget = terrain.playersList.find { it.playerId == action.playerTargetId }
        return playerTarget?.applyAction(action) ?: ActionToke.fromAction(action).apply {
            wasError = true
        }
    }

    private fun actionPass(action: Action): ActionToke {
        return ActionToke.fromAction(action)
    }

    private fun actionRun(action: Action): ActionToke {
        val playerTarget = terrain.playersList.find { it.playerId == action.playerId }
        terrain.playersList.remove(playerTarget)

        return ActionToke.fromAction(action)
    }

    fun evaluateRound() {}

    /**
     * Round Result
     * Resume actual data into terrain
     */

    fun roundResult() {}

    fun copy(): GameEngine {
        return GameEngine().apply {
            initConfiguration(this.terrain)
        }
    }
}