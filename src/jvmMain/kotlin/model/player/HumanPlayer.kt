package model.player

import model.action.Action
import model.hero.move.boosts.NegativeBoost
import model.hero.move.boosts.PositiveBoost
import model.result.ActionToke

class HumanPlayer(
    override val name: String,
    override val playerId: String,
    val lifePoints: Int,
    var lifePointsLose: Int
) : AbstractPlayer() {
    override fun applyAction(action: Action): ActionToke {
        action.playerTarget_Boost_Toke?.let {
            if (it is PositiveBoost) {
                if (lifePointsLose < it.value) lifePointsLose = 0
                else if (lifePointsLose > 0) lifePointsLose -= it.value
            }

            if (it is NegativeBoost) {
                lifePointsLose += it.value
            }
        }

        return ActionToke.fromAction(action).apply {
            wasError = false
        }
    }
}