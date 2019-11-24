package azamat.kz.loginactivity.mobileui.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import azamat.kz.loginactivity.R
import azamat.kz.loginactivity.mobileui.utils.showAlert
import azamat.kz.loginactivity.presentation.login.LoginViewModel
import azamat.kz.loginactivity.remote.model.ResourceState
import kotlinx.android.synthetic.main.a_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_login)
        viewModel.checkIsLogin()

        viewModel.liveData.observe(this, Observer { t ->
            handlerState(t)
        })

        btnSingIn.setOnClickListener {
            viewModel.getLogin(edEmail.text.toString(), edPassword.text.toString())
        }
    }

    private fun handlerState(t: String?) {
        if (t != null) {
            when (t) {
                ResourceState.SUCCESS.name -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                ResourceState.ERROR.name -> {
                    hideProgress()
                    showAlert("Ошибка")
                }
                ResourceState.LOADING.name -> {
                    displayProgress()
                }
                LoginViewModel.LoginState.ERROR_INPUT_EMAIL.name -> {
                    showAlert("Введите емейл")
                }
                LoginViewModel.LoginState.ERROR_INPUT_PASSWORD.name -> {
                    showAlert("Введите пароль")
                }
                LoginViewModel.LoginState.ERROR_NOT_VALID_EMAIL.name -> {
                    showAlert("Введен неправильный формат электронной почты")
                }
                LoginViewModel.LoginState.ERROR_PASSWORD_LENGTH.name -> {
                    showAlert("Пароль должен состоять минимум из 8 символов")
                }
                LoginViewModel.LoginState.CONNECTION_ERROR.name -> {
                    hideProgress()
                    showAlert("Ошибка! Проверьте подклюсение к интернету")
                }
            }
        }
    }

    private fun displayProgress() {
        progressBar.visibility = View.VISIBLE
        inputContainer.visibility = View.GONE
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
        inputContainer.visibility = View.VISIBLE
    }
}
