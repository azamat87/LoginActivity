package azamat.kz.loginactivity.remote.mapper

import azamat.kz.loginactivity.remote.model.CurrentAccount
import azamat.kz.loginactivity.remote.model.Resource
import azamat.kz.loginactivity.remote.model.ResourceState
import retrofit2.Response

class AccountModelMapper : ModelMapper<Response<CurrentAccount>, Resource<CurrentAccount>> {

    override fun mapFromModel(model: Response<CurrentAccount>): Resource<CurrentAccount> {
        if (model.isSuccessful) {
            return Resource(ResourceState.SUCCESS, model.body(), null, null)
        }
        return Resource(ResourceState.ERROR, null, model.errorBody(), null)
    }

    fun isLogoutSuccess(model: Response<Any>): Resource<Any> {
        if (model.isSuccessful) {
            return Resource(ResourceState.SUCCESS, model.body(), null, null)
        }
        return Resource(ResourceState.ERROR, null, model.errorBody(), null)
    }
}