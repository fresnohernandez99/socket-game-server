package model.hero

import model.hero.armor.AbstractArmor
import model.hero.move.boosts.AbstractBoost
import model.hero.classes.AbstractClass
import model.effect.AbstractEffect
import model.hero.stat.AbstractStat
import model.terrain.AbstractPiece

class Hero(
    val lifePoints: Int,
    val level: Int,
    val charClass: AbstractClass,
    var boosts: ArrayList<AbstractBoost>,
    var effects: ArrayList<AbstractEffect>,
    var stats: ArrayList<AbstractStat>,
    var armor: ArrayList<AbstractArmor>
) : AbstractPiece()