package azamat.kz.loginactivity.remote.main

import azamat.kz.loginactivity.remote.service.RestService
import azamat.kz.loginactivity.remote.mapper.AccountModelMapper
import azamat.kz.loginactivity.remote.model.CurrentAccount
import azamat.kz.loginactivity.remote.model.Resource
import azamat.kz.loginactivity.remote.model.SessionModel

class MainRepositoryImpl constructor(val restService: RestService, val mapper: AccountModelMapper): MainRepository{

    override suspend fun logout(token: String, session: SessionModel): Resource<Any> {
        return mapper.isLogoutSuccess(restService.logout(token, session).await())
    }

    override suspend fun getProfile(token: String): Resource<CurrentAccount> {
        return mapper.mapFromModel(restService.accountInfo(token).await())
    }
}