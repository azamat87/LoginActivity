package azamat.kz.loginactivity.remote.service

import azamat.kz.loginactivity.remote.model.CurrentAccount
import azamat.kz.loginactivity.remote.model.LoginModel
import azamat.kz.loginactivity.remote.model.SessionModel
import azamat.kz.loginactivity.remote.model.TokenModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RestService {

    @POST("/accounts/auth")
    fun loginUser(@Body body: LoginModel): Deferred<Response<TokenModel>>

    @POST("/accounts/sessions/refresh")
    fun refreshToken(@Header("Authorization") token: String): Deferred<Response<TokenModel>>

    @POST("/accounts/sessions/end")
    fun logout(@Header("Authorization") token: String, @Body sessionModel: SessionModel): Deferred<Response<Any>>

    @GET("/accounts/current")
    fun accountInfo(@Header("Authorization") token: String): Deferred<Response<CurrentAccount>>


}