package model.hero.classes

import model.hero.stats.AbstractStat

abstract class AbstractClass {
    abstract val name: String
    abstract val poweredStat: AbstractStat
}