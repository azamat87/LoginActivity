package azamat.kz.loginactivity.remote.mapper

import azamat.kz.loginactivity.remote.model.Resource
import azamat.kz.loginactivity.remote.model.ResourceState
import azamat.kz.loginactivity.remote.model.TokenModel
import retrofit2.Response


class LoginModelMapper: ModelMapper<Response<TokenModel>, Resource<TokenModel>> {

    override fun mapFromModel(model: Response<TokenModel>): Resource<TokenModel> {
        if (model.isSuccessful) {
            return Resource(ResourceState.SUCCESS, model.body(), null, null)
        }
        return Resource(ResourceState.ERROR, null, model.errorBody(), null)
    }
}