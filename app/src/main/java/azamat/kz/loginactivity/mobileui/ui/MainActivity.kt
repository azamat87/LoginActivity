package azamat.kz.loginactivity.mobileui.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import azamat.kz.loginactivity.R
import azamat.kz.loginactivity.mobileui.adapters.ItemRvAdapter
import azamat.kz.loginactivity.mobileui.utils.showAlert
import azamat.kz.loginactivity.presentation.main.MainViewModel
import azamat.kz.loginactivity.remote.model.ResourceState
import kotlinx.android.synthetic.main.a_login.progressBar
import kotlinx.android.synthetic.main.a_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val mAdapter = ItemRvAdapter()

    private var totalCount = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_main)

        iniRvTransactions()
        viewModel.getProfile()
        profileInit()
        stateInit()
        initButtons()
    }

    private fun iniRvTransactions() {
        rvTransactions.layoutManager = LinearLayoutManager(this)
        rvTransactions.adapter = mAdapter
        viewModel.transactionLiveData.observe(this, Observer {
            val value = it.x!!.inputs[0].prev_out!!.value
            val format: Double = value!!.toDouble() / 100000000
            totalCount += format
            mAdapter.addItems(it)
            tvTotalCount.text = String.format("Сумма: %.8f BTC", totalCount)
        })
    }

    private fun initButtons() {
        btnLogout.setOnClickListener {
            viewModel.logout()
        }
        btnStart.setOnClickListener {
            viewModel.subscribeSocket()
        }

        btnClear.setOnClickListener {
            totalCount = 0.0
            tvTotalCount.text = ""
            mAdapter.clearItems()
        }

        btnStop.setOnClickListener {
            viewModel.unSubscribeSocket()
        }
    }

    private fun stateInit() {
        viewModel.liveDataState.observe(this, Observer { t ->
            handlerState(t)
        })
    }

    private fun handlerState(t: String?) {
        if (t != null) {
            when (t) {
                ResourceState.SUCCESS.name -> {

                }
                ResourceState.ERROR.name -> {
                    hideProgress()
                    showAlert("Ошибка")
                }
                ResourceState.LOADING.name -> {
                    displayProgress()
                }
                MainViewModel.ProfileState.LOGOUT_SUCCESS.name -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                MainViewModel.ProfileState.CONNECTION_ERROR.name -> {
                    hideProgress()
                    showAlert("Ошибка! Проверьте подклюсение к интернету")
                }
            }
        }
    }

    private fun profileInit() {
        viewModel.profileLiveData.observe(this, Observer { t ->
            hideProgress()
            tvProfile.text = "Имя: ${t.first_name} ${t.last_name}\nEmail: ${t.email}"
            start()
        })
    }

    private fun displayProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    private fun start() {
        viewModel.subscribeSocket()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unSubscribeSocket()
    }
}
