package model.terrain

import model.action.Action
import model.result.ActionToke

abstract class AbstractPiece{
    abstract val id: String
    abstract var playerId: String
    abstract var position: String?
    abstract var type: String
    abstract fun applyAction(action: Action): ActionToke

    companion object {
        const val PIECE_NAME = "abstract-name"
    }
}