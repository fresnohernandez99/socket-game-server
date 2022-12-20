package model.player

import model.action.Action
import model.result.ActionToke

abstract class AbstractPlayer {
    abstract val name: String
    abstract val playerId: String

    abstract fun applyAction(action: Action): ActionToke
}