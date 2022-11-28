package model.hero.classes

import model.hero.stat.AbstractStat

abstract class AbstractClass {
    abstract val name: String
    abstract val poweredStat: AbstractStat
}