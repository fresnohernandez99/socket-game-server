package model.hero.moves

import model.hero.effects.EffectType

class EffectsHeal(
    override val id: String,
    override val name: String,
    override val times: Int,
    val effectTarget: EffectType
) : AbstractMove()