package azamat.kz.loginactivity.remote.login

import azamat.kz.loginactivity.remote.model.LoginModel
import azamat.kz.loginactivity.remote.model.Resource
import azamat.kz.loginactivity.remote.model.TokenModel

interface LoginRepository {

    suspend fun loginUser(login: LoginModel): Resource<TokenModel>

    suspend fun refreshToken(token: String): Resource<TokenModel>

}