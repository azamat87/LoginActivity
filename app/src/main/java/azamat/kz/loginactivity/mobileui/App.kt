package azamat.kz.loginactivity.mobileui

import android.app.Application
import azamat.kz.loginactivity.mobileui.injection.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(module))
        }
    }

}