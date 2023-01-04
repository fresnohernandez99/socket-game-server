package model.terrain

import model.player.AbstractPlayer
import model.terrain.grid.SpaceGrid
import model.terrain.rules.AbstractRule
import model.terrain.space.Space

class Terrain(
    val space: Space,
    var rules: List<AbstractRule>,
    var playersList: List<AbstractPlayer> = ArrayList(),
    var loserPlayers: List<AbstractPlayer> = ArrayList(),
    val spaceGrid: SpaceGrid = SpaceGrid()
) {
    fun copy() = Terrain(space, rules)
}