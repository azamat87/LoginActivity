package azamat.kz.loginactivity.domain.main

import azamat.kz.loginactivity.data.pref.PREF_IS_LOGIN
import azamat.kz.loginactivity.data.pref.PREF_TOKEN
import azamat.kz.loginactivity.data.pref.PreferenceManager
import azamat.kz.loginactivity.remote.socket.SocketRecieverListener
import azamat.kz.loginactivity.remote.main.MainRepository
import azamat.kz.loginactivity.remote.model.CurrentAccount
import azamat.kz.loginactivity.remote.model.Resource
import azamat.kz.loginactivity.remote.model.SessionModel
import azamat.kz.loginactivity.remote.model.TransactionModel
import azamat.kz.loginactivity.remote.socket.SocketRepository

class MainUseCaseImpl constructor(val repository: MainRepository,
                                  val socketRepository: SocketRepository,
                                  val manager: PreferenceManager)
    : MainUseCase, SocketRecieverListener {

    private var mListener: SocketRecieverListener?= null

    override fun setListener(listener: SocketRecieverListener) {
        mListener = listener
    }

    override fun setMessage(transaction: TransactionModel) {
        if (mListener != null) {
            mListener!!.setMessage(transaction)
        }
    }

    override fun subscribe() {
        socketRepository.setListener(this)
        socketRepository.subscribe()
    }

    override fun unSubscribe() {
        socketRepository.unSubscribe()
    }

    override fun getToken(): String {
        return manager.getPrefString(PREF_TOKEN)!!
    }

    override fun isLogin(): Boolean {
        return manager.getPrefBoolean(PREF_IS_LOGIN)!!
    }

    override fun setLogin(state: Boolean) {
        manager.setPrefBoolean(PREF_IS_LOGIN, state)
    }

    override fun saveToken(token: String) {
        manager.setPrefString(PREF_TOKEN, token)
    }

    override suspend fun getProfile(): Resource<CurrentAccount> {
        return repository.getProfile(manager.getPrefString(PREF_TOKEN)!!)
    }

    override suspend fun logout(session: SessionModel): Resource<Any> {
        return repository.logout(manager.getPrefString(PREF_TOKEN)!!, session)
    }
}