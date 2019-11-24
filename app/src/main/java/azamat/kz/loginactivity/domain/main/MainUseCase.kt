package azamat.kz.loginactivity.domain.main

import azamat.kz.loginactivity.domain.base.BaseUseCase
import azamat.kz.loginactivity.remote.socket.SocketRecieverListener
import azamat.kz.loginactivity.remote.model.CurrentAccount
import azamat.kz.loginactivity.remote.model.Resource
import azamat.kz.loginactivity.remote.model.SessionModel

interface MainUseCase: BaseUseCase {

    suspend fun getProfile(): Resource<CurrentAccount>

    suspend fun logout(session: SessionModel): Resource<Any>

    fun subscribe()

    fun unSubscribe()

    fun setListener(listener: SocketRecieverListener)
}