package azamat.kz.loginactivity.remote.socket

import azamat.kz.loginactivity.remote.mapper.TransactionMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class SocketRepositoryImpl(private val mapper: TransactionMapper): SocketRepository {

    private val client: OkHttpClient = OkHttpClient()
    private var ws: WebSocket? = null
    private val SOCKET_URL = "wss://ws.blockchain.info/inv"
    private val SUBSCRIBE = "{op:unconfirmed_sub}"
    private val UNSUBSCRIBE = "{op:unconfirmed_unsub}"

    private var listener: SocketRecieverListener? = null

    init {
        val request = Request.Builder().url(SOCKET_URL).build()
        val listener = MWebSocketListener()
        ws = client.newWebSocket(request, listener)
        ws!!.request()
        ws!!.send(SUBSCRIBE)
    }

    override fun setListener(listener: SocketRecieverListener) {
        this.listener = listener
    }

    override fun subscribe() {
        if (ws != null) {
            ws!!.send(SUBSCRIBE)
        }
    }

    override fun unSubscribe() {
        if (ws != null) {
            ws!!.send(UNSUBSCRIBE)
        }
    }

    inner class MWebSocketListener : WebSocketListener() {

        override fun onMessage(webSocket: WebSocket, text: String) {
            if (listener != null)
                listener!!.setMessage(mapper.mapFromModel(text))
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        }
    }
}