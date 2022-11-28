package model.effect

abstract class AbstractEffect {
    abstract val id: String
    abstract val type: EffectType
    abstract val attrTarget: String
    abstract val positive: Boolean

    abstract fun effect(): AbstractEffect
}