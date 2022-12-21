package engine

import model.action.Action
import model.action.ActionType.*
import model.player.AbstractPlayer
import model.result.ActionToke
import model.terrain.Terrain
import model.terrain.rules.AbstractRule
import model.terrain.space.Space
import model.terrain.space.SpaceConfiguration

class GameEngine {

    private lateinit var terrain: Terrain

    /**
     * Iniciado por el servidor una única vez por tipo de juego.
     * Objeto global.
     */
    fun initConfiguration(
        configuration: SpaceConfiguration,
        initRules: ArrayList<AbstractRule> = ArrayList(),
    ): Terrain {
        if (!this::terrain.isInitialized)
            terrain = Terrain(
                space = Space(configuration),
                rules = initRules
            )
        return terrain
    }

    /**
     * Método por partida.
     * Devuelve el formato de juego.
     * Usado para actualizar el estado inicial de los clientes.
     */
    fun sendConfigurations(): Terrain {
        return terrain
    }

    /**
     * Battle
     * Receive all data from players
     * Se activa al tener todos los players listos. (SOCKET)
     */
    fun initBattle(playersList: ArrayList<AbstractPlayer>): Terrain {
        return terrain.apply {
            this.playersList = playersList

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
    fun receivePlayerActionsAndRun(playerActions: ArrayList<ArrayList<Action>>) {
        playerActions.forEach {
            applyActions(it)
        }
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
                    NO_TARGET -> {}
                    PASS -> {}
                    RUN -> {}
                    PLAYER_ITEM -> {}
                    PIECE_ITEM -> {}
                    FIELD_ITEM -> {}
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