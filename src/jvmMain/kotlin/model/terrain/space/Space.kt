package model.terrain.space

class Space(
    val configuration: SpaceConfiguration,
    private var playersState: PlayersState? = null
) {
    private var inited = false

    fun setState(playersState: PlayersState) {
        if (!inited) {
            this.playersState = playersState
            inited = true
        }
    }
}