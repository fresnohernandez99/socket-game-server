package model.hero

import model.action.Action
import model.hero.classes.AbstractClass
import model.hero.equipment.AbstractEquipment
import model.hero.move.*
import model.hero.move.AbstractBoost
import model.hero.stat.AbstractStat
import model.result.ActionToke
import model.terrain.AbstractPiece

class Hero(
    override val id: String,
    override var playerId: String,
    override var position: Int? = null,
    val lifePoints: Int,
    var lifePointsLose: Int,
    val level: Int,
    val charClass: AbstractClass,
    var moves: ArrayList<AbstractMove> = ArrayList(),

    /**
     * Stats order in array:
     * 0- Defense
     * 1- Evasion
     * 2- Intellect
     * 3- Magic
     * 4- Speed
     * 5- Strength
     */
    var stats: ArrayList<AbstractStat>,
    var equipment: ArrayList<AbstractEquipment> = ArrayList()
) : AbstractPiece() {
    override fun applyAction(action: Action): ActionToke {
        action.move?.let { move ->
            // Recibo un ataque calculo el danno que me aporta
            if (move is AttackMove) {
                // calcular probabilidad de fallo
                val r = Math.random() * 100
                val isMiss = r <= stats[2].value

                var attackDamage = 0
                if (!isMiss) {
                    // calcular danno final
                    attackDamage = move.damage - (
                            move.damage *
                                    stats[0].value / 100
                            ).toInt()
                    // agregar danno tomado
                    lifePointsLose += attackDamage
                }

                return ActionToke.fromAction(action).apply {
                    wasMiss = isMiss
                }
            }

            // Aplico un movimiento defensivo. Se define como mejorar el stat Defense
            if (move is DefenseMove) {
                stats[0].value += move.percent
            }

            // Aplico un movimiento de curación instantáneo. Se devuelve a cero los puntos perdidos
            if (move is InstantHealMode) {
                if (lifePointsLose > 0) lifePointsLose = 0
            }

            // Aplico un movimiento de curación. Se define como restar a lifePointsLose si este es > 0
            if (move is HealMove) {
                if (lifePointsLose < move.restoredPoints) lifePointsLose = 0
                else if (lifePointsLose > 0) lifePointsLose -= move.restoredPoints
            }

            if (move is AbstractBoost) {
                stats[move.attrToBoost].value += move.value
            }
        }

        // devolver que si se aplicó si no es de tipo ataque el cual puede fallar
        return ActionToke.fromAction(action).apply {
            wasMiss = false
        }
    }
}