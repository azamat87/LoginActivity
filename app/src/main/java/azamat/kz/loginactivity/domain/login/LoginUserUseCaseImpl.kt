package azamat.kz.loginactivity.domain.login

import azamat.kz.loginactivity.data.pref.PREF_IS_LOGIN
import azamat.kz.loginactivity.data.pref.PREF_TOKEN
import azamat.kz.loginactivity.data.pref.PreferenceManager
import azamat.kz.loginactivity.remote.login.LoginRepository
import azamat.kz.loginactivity.remote.model.LoginModel
import azamat.kz.loginactivity.remote.model.Resource
import azamat.kz.loginactivity.remote.model.TokenModel

class LoginUserUseCaseImpl(val repository: LoginRepository, val manager: PreferenceManager):
    LoginUserUseCase {

    override suspend fun refreshToken(): Resource<TokenModel> {
        return repository.refreshToken(getToken())
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

    override suspend fun doWork(params: LoginModel): Resource<TokenModel> {
        return repository.loginUser(params)
    }
}