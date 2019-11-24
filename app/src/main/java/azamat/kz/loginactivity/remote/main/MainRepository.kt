package azamat.kz.loginactivity.remote.main

import azamat.kz.loginactivity.remote.model.CurrentAccount
import azamat.kz.loginactivity.remote.model.Resource
import azamat.kz.loginactivity.remote.model.SessionModel

interface MainRepository {

    suspend fun getProfile(token: String): Resource<CurrentAccount>

    suspend fun logout(token: String, session: SessionModel): Resource<Any>
}