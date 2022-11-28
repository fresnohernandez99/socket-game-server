package model.hero

import model.hero.armor.AbstractArmor
import model.hero.moves.boosts.AbstractBoost
import model.hero.classes.AbstractClass
import model.hero.effects.AbstractEffect
import model.hero.items.AbstractItem
import model.hero.stats.AbstractStat
import model.terrain.AbstractPiece

class Hero(
    val lifePoints: Int,
    val level: Int,
    val charClass: AbstractClass,
    var boosts: ArrayList<AbstractBoost>,
    var effects: ArrayList<AbstractEffect>,
    var stats: ArrayList<AbstractStat>,
    var armor: ArrayList<AbstractArmor>,
    var items: ArrayList<AbstractItem>
) : AbstractPiece()