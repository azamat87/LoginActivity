package azamat.kz.loginactivity.remote.login

import azamat.kz.loginactivity.remote.service.RestService
import azamat.kz.loginactivity.remote.mapper.LoginModelMapper
import azamat.kz.loginactivity.remote.model.LoginModel
import azamat.kz.loginactivity.remote.model.Resource
import azamat.kz.loginactivity.remote.model.TokenModel

class LoginRepositoryImpl constructor(val restService: RestService, val mapper: LoginModelMapper) :
    LoginRepository {

    override suspend fun refreshToken(token: String): Resource<TokenModel> {
        return mapper.mapFromModel(restService.refreshToken(token).await())
    }

    override suspend fun loginUser(login: LoginModel): Resource<TokenModel> {
        return mapper.mapFromModel(restService.loginUser(login).await())
    }
}