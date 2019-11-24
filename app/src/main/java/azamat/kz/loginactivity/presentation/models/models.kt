package azamat.kz.loginactivity.presentation.models

data class TokenBody(val exp: Long, val account_id: String, val session_id: String, val account_type: String)