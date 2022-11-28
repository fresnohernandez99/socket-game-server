package model.hero.move

import model.effect.EffectType

class EffectsHeal(
    override val id: String,
    override val name: String,
    override val times: Int,
    val effectTarget: EffectType
) : AbstractMove()