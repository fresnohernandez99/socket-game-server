package model.result

import model.player.AbstractPlayer

class RoundResult(
    var playersReceive: ArrayList<ActionToke> = ArrayList(),
    var loserPlayers: ArrayList<AbstractPlayer> = ArrayList()
)