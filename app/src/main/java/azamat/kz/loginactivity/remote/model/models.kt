package azamat.kz.loginactivity.remote.model

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

class LoginModel(var email:String ?= "", var password: String ?= "")

data class TokenModel(val token: String?)

data class CurrentAccount(val info: InfoModel?)

data class InfoModel(val profiles: ArrayList<ProfileModel>)

data class ProfileModel(val first_name: String?,val last_name: String?, val email: String?)

data class Resource<out T> constructor(val status: ResourceState,
                                  val data: T?,
                                  val errorBody: ResponseBody?,
                                  val message: String?)

data class SessionModel(val session_id: String?)

data class TransactionModel(@SerializedName("op") val op: String?,
                            @SerializedName("x") val x: TransactionX?)

data class TransactionX(@SerializedName("hash") val hash: String?,
                        @SerializedName("inputs") val inputs: ArrayList<Inputs>)

data class Inputs(@SerializedName("sequence") val sequence: Long?,
                  @SerializedName("prev_out") val prev_out: PrevOut?)

data class PrevOut(@SerializedName("value") val value: Long?)
