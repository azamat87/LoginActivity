package azamat.kz.loginactivity.domain.base

interface BaseUseCase{

    fun saveToken(token: String)

    fun getToken(): String

    fun isLogin(): Boolean

    fun setLogin(state: Boolean)
}