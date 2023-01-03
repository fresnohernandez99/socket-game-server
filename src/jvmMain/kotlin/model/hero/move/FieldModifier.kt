package model.hero.move

class FieldModifier(
    override val id: String,
) : AbstractMove() {
    fun effect(field: Any?) {} //todo change ** any ** for Field Type
}