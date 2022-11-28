package model.terrain

import model.player.IPlayer
import model.terrain.rules.AbstractRule
import model.terrain.space.Space

class Terrain(
    val space: Space,
    var rules: ArrayList<AbstractRule>,
    var playersList: ArrayList<IPlayer> = ArrayList()
)