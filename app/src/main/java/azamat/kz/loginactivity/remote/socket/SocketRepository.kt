package azamat.kz.loginactivity.remote.socket

interface SocketRepository {

    fun subscribe()

    fun unSubscribe()

    fun setListener(listener: SocketRecieverListener)
}