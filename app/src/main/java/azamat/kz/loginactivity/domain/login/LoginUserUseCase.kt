package azamat.kz.loginactivity.domain.login

import azamat.kz.loginactivity.domain.base.BaseUseCase
import azamat.kz.loginactivity.remote.model.LoginModel
import azamat.kz.loginactivity.remote.model.Resource
import azamat.kz.loginactivity.remote.model.TokenModel

interface LoginUserUseCase: BaseUseCase {

    suspend fun doWork(params: LoginModel): Resource<TokenModel>

    suspend fun refreshToken(): Resource<TokenModel>

}