import java.util.concurrent.CopyOnWriteArraySet

class SocketManager {
    private val socketEndpoints: MutableSet<SocketEndpoint> = CopyOnWriteArraySet()
    private val users: HashMap<String, String> = HashMap()
}