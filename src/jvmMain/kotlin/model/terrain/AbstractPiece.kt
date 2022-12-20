package model.terrain

import model.action.Action
import model.result.ActionToke

abstract class AbstractPiece {
    abstract val id: String
    abstract var playerId: String
    abstract var position: Int?

    abstract fun applyAction(action: Action): ActionToke
}