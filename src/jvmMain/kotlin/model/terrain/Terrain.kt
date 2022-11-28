package model.terrain

import model.player.AbstractPlayer
import model.terrain.rules.AbstractRule
import model.terrain.space.Space

class Terrain(
    val space: Space,
    var rules: ArrayList<AbstractRule>,
    var playersList: ArrayList<AbstractPlayer> = ArrayList()
)