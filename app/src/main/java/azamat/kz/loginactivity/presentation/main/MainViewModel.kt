package azamat.kz.loginactivity.presentation.main

import androidx.lifecycle.MutableLiveData
import azamat.kz.loginactivity.domain.main.MainUseCase
import azamat.kz.loginactivity.presentation.base.BaseViewModel
import azamat.kz.loginactivity.presentation.utils.decodedGetBody
import azamat.kz.loginactivity.remote.socket.SocketRecieverListener
import azamat.kz.loginactivity.remote.model.ProfileModel
import azamat.kz.loginactivity.remote.model.ResourceState
import azamat.kz.loginactivity.remote.model.SessionModel
import azamat.kz.loginactivity.remote.model.TransactionModel

class MainViewModel constructor(private val useCase: MainUseCase): BaseViewModel() {

    val liveDataState = MutableLiveData<String>()
    val profileLiveData = MutableLiveData<ProfileModel>()
    val transactionLiveData = MutableLiveData<TransactionModel>()

    override fun connectionError() {
        super.connectionError()
        setToLiveDataState(ProfileState.CONNECTION_ERROR.name)
    }

    fun getProfile() {
        setToLiveDataState(ResourceState.LOADING.name)

        doWork {
            val account = useCase.getProfile()
            if (account.status == ResourceState.SUCCESS) {
                doWorkInMainThread {
                    profileLiveData.value = account.data!!.info!!.profiles[0]
                }
            }
        }
    }

    fun logout() {
        doWork {
            val body = tokenToModel(decodedGetBody(useCase.getToken())!!)
            val logout = useCase.logout(SessionModel(body.session_id))
            if (logout.status == ResourceState.SUCCESS) {
                useCase.setLogin(false)
                useCase.saveToken("")
                setToLiveDataState(ProfileState.LOGOUT_SUCCESS.name)
            } else {
                setToLiveDataState(ResourceState.ERROR.name)
            }
        }
    }

    fun subscribeSocket() {
        useCase.setListener(object : SocketRecieverListener {
            override fun setMessage(transaction: TransactionModel) {
                doWorkInMainThread{
                    transactionLiveData.value = transaction
                }
            }
        })
        useCase.subscribe()
    }

    fun unSubscribeSocket() {
        useCase.unSubscribe()
    }

    private fun setToLiveDataState(state: String) {
        doWorkInMainThread {
            liveDataState.value = state
        }
    }

    enum class ProfileState{
        LOGOUT_SUCCESS, CONNECTION_ERROR
    }
}