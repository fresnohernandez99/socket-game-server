package socket.model.request

import model.terrain.space.SpaceConfiguration

class UpdateConfigurationsRequest(
    val roomId: String,
    var spaceConfiguration: SpaceConfiguration
)