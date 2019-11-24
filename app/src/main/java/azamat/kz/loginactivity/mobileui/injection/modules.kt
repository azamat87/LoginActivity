package azamat.kz.loginactivity.mobileui.injection

import azamat.kz.loginactivity.data.pref.PreferenceManager
import azamat.kz.loginactivity.domain.login.LoginUserUseCase
import azamat.kz.loginactivity.domain.login.LoginUserUseCaseImpl
import azamat.kz.loginactivity.domain.main.MainUseCase
import azamat.kz.loginactivity.domain.main.MainUseCaseImpl
import azamat.kz.loginactivity.presentation.login.LoginViewModel
import azamat.kz.loginactivity.presentation.main.MainViewModel
import azamat.kz.loginactivity.remote.service.RestService
import azamat.kz.loginactivity.remote.login.LoginRepository
import azamat.kz.loginactivity.remote.login.LoginRepositoryImpl
import azamat.kz.loginactivity.remote.main.MainRepository
import azamat.kz.loginactivity.remote.main.MainRepositoryImpl
import azamat.kz.loginactivity.remote.mapper.AccountModelMapper
import azamat.kz.loginactivity.remote.mapper.LoginModelMapper
import azamat.kz.loginactivity.remote.mapper.TransactionMapper
import azamat.kz.loginactivity.remote.socket.SocketRepository
import azamat.kz.loginactivity.remote.socket.SocketRepositoryImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single { PreferenceManager(get()) }
    single<RestService> { getRetrofit() }
    single<LoginModelMapper> { LoginModelMapper() }
    single<AccountModelMapper> { AccountModelMapper() }
    single<TransactionMapper> { TransactionMapper() }

    single<LoginRepository> {
        LoginRepositoryImpl(get(), get())
    }
    single<MainRepository> {
        MainRepositoryImpl(get(), get())
    }

    single<SocketRepository> {
        SocketRepositoryImpl(get())
    }

    single<LoginUserUseCase> {
        LoginUserUseCaseImpl(
            get(),
            get()
        )
    }
    single<MainUseCase> {
        MainUseCaseImpl(
            get(),
            get(),
            get()
        )
    }

    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) }

}