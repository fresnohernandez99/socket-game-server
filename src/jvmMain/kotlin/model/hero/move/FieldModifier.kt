package model.hero.move

class FieldModifier(
    override val id: String,
    override val name: String,
) : AbstractMove() {
    fun effect(field: Any?) {} //todo change ** any ** for Field Type
}