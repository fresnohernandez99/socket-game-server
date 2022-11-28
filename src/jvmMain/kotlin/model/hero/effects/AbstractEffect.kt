package model.hero.effects

abstract class AbstractEffect {
    abstract val id: String
    abstract val type: EffectType
    abstract val attrTarget: String
    abstract val positive: Boolean

    abstract fun effect(): AbstractEffect
}