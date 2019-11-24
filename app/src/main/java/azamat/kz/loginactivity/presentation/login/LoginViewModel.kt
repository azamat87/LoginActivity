package azamat.kz.loginactivity.presentation.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import azamat.kz.loginactivity.domain.login.LoginUserUseCase
import azamat.kz.loginactivity.presentation.base.BaseViewModel
import azamat.kz.loginactivity.presentation.utils.decodedGetBody
import azamat.kz.loginactivity.remote.model.LoginModel
import azamat.kz.loginactivity.remote.model.ResourceState
import com.google.gson.Gson

class LoginViewModel constructor(private val useCase: LoginUserUseCase): BaseViewModel() {

    val liveData = MutableLiveData<String>()

    override fun connectionError() {
        super.connectionError()
        setToLiveDataState(LoginState.CONNECTION_ERROR.name)
    }

    fun getLogin(login: String? = "", password: String? = "") {

        if (login == null || login.isEmpty()) {
            setToLiveDataState(LoginState.ERROR_INPUT_EMAIL.name)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()) {
            setToLiveDataState(LoginState.ERROR_NOT_VALID_EMAIL.name)
        } else if (password == null || password.isEmpty()) {
            setToLiveDataState(LoginState.ERROR_INPUT_PASSWORD.name)
        } else if (password.length < 8) {
            setToLiveDataState(LoginState.ERROR_PASSWORD_LENGTH.name)
        } else {
            startRequest(login, password)
        }
    }

    private fun startRequest(login: String, password: String) {
        setToLiveDataState(ResourceState.LOADING.name)

        doWork {
            val any = useCase.doWork(LoginModel(login, password))
            if (any.status == ResourceState.SUCCESS) {
                Log.e("myLog", "login " + decodedGetBody(any.data!!.token!!))
                if (any.status == ResourceState.SUCCESS) {
                    useCase.saveToken(any.data.token!!)
                    useCase.setLogin(true)
                    setToLiveDataState(ResourceState.SUCCESS.name)
                }
            } else {
                setToLiveDataState(ResourceState.ERROR.name)
            }
        }
    }

    fun checkIsLogin() {
        if (useCase.isLogin()) {
            val body = tokenToModel(decodedGetBody(useCase.getToken())!!)
            Log.e("myLog", "${body.exp} = " + System.currentTimeMillis()/1000)
            if (body.exp < System.currentTimeMillis()/1000) {
                liveData.value = ResourceState.LOADING.name
                doWork {
                    val any = useCase.refreshToken()
                    if (any.status == ResourceState.SUCCESS) {
                        useCase.saveToken(any.data!!.token!!)
                        setToLiveDataState(ResourceState.SUCCESS.name)
                    } else {
                        Log.e("myLog", " ERROR= " + Gson().toJson(any))
                        setToLiveDataState(ResourceState.ERROR.name)
                    }
                }
            } else {
                setToLiveDataState(ResourceState.SUCCESS.name)
            }
        }
    }

    private fun setToLiveDataState(state: String) {
        doWorkInMainThread {
            liveData.value = state
        }
    }

    enum class LoginState{
        ERROR_INPUT_EMAIL,
        ERROR_INPUT_PASSWORD,
        ERROR_NOT_VALID_EMAIL,
        ERROR_PASSWORD_LENGTH,
        CONNECTION_ERROR
    }

}