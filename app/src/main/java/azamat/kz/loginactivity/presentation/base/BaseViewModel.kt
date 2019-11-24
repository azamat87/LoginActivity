package azamat.kz.loginactivity.presentation.base

import androidx.lifecycle.ViewModel
import azamat.kz.loginactivity.presentation.models.TokenBody
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

open class BaseViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var isActive = true

    // Do work in IO
    fun <P> doWork(doOnAsyncBlock: suspend CoroutineScope.() -> P) {
        doCoroutineWork(doOnAsyncBlock, viewModelScope, IO)
    }

    // Do work in Main
    // doWorkInMainThread {...}
    fun <P> doWorkInMainThread(doOnAsyncBlock: suspend CoroutineScope.() -> P) {
        doCoroutineWork(doOnAsyncBlock, viewModelScope, Main)
    }

    // Do work in IO repeately
    // doRepeatWork(1000) {...}
    // then we need to stop it calling stopRepeatWork()
    fun <P> doRepeatWork(delay: Long, doOnAsyncBlock: suspend CoroutineScope.() -> P) {
        isActive = true
        viewModelScope.launch {
            while (this@BaseViewModel.isActive) {
                withContext(IO) {
                    doOnAsyncBlock.invoke(this)
                }
                if (this@BaseViewModel.isActive) {
                    delay(delay)
                }
            }
        }
    }

    fun stopRepeatWork() {
        isActive = false
    }

    override fun onCleared() {
        super.onCleared()
        isActive = false
        viewModelJob.cancel()
    }


    private inline fun <P> doCoroutineWork(
        crossinline doOnAsyncBlock: suspend CoroutineScope.() -> P,
        coroutineScope: CoroutineScope,
        context: CoroutineContext
    ) {
        coroutineScope.launch {
            withContext(context) {
                try {
                    doOnAsyncBlock.invoke(this)
                } catch (e: UnknownHostException) {
                    e.printStackTrace()
                    connectionError()
                } catch (e: SocketTimeoutException) {
                    e.printStackTrace()
                    connectionError()
                }
            }
        }
    }

    fun tokenToModel(token: String): TokenBody {
        return Gson().fromJson(token, TokenBody::class.java)
    }

    open fun connectionError(){

    }

}