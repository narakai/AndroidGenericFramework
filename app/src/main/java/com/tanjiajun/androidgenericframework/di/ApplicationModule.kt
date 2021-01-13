package com.tanjiajun.androidgenericframework.di

import com.tanjiajun.androidgenericframework.AndroidGenericFrameworkConfiguration
import com.tanjiajun.androidgenericframework.data.local.user.UserLocalDataSource
import com.tanjiajun.androidgenericframework.data.remote.BasicAuthInterceptor
import com.tanjiajun.androidgenericframework.data.remote.repository.RepositoryRemoteDataSource
import com.tanjiajun.androidgenericframework.data.remote.user.UserRemoteDataSource
import com.tanjiajun.androidgenericframework.data.repository.GitHubRepository
import com.tanjiajun.androidgenericframework.data.repository.UserInfoRepository
import com.tanjiajun.androidgenericframework.ui.main.activity.MainActivity
import com.tanjiajun.androidgenericframework.ui.main.activity.SplashActivity
import com.tanjiajun.androidgenericframework.ui.main.viewmodel.MainViewModel
import com.tanjiajun.androidgenericframework.ui.main.viewmodel.SplashViewModel
import com.tanjiajun.androidgenericframework.ui.repository.fragment.RepositoryFragment
import com.tanjiajun.androidgenericframework.ui.repository.viewmodel.RepositoryViewModel
import com.tanjiajun.androidgenericframework.ui.user.activity.PersonalCenterActivity
import com.tanjiajun.androidgenericframework.ui.user.activity.RegisterAndLoginActivity
import com.tanjiajun.androidgenericframework.ui.user.fragment.LoginFragment
import com.tanjiajun.androidgenericframework.ui.user.fragment.LoginFragment2
import com.tanjiajun.androidgenericframework.ui.user.viewmodel.LoginViewModel
import com.tanjiajun.androidgenericframework.ui.user.viewmodel.PersonalCenterViewModel
import com.tencent.mmkv.MMKV
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by TanJiaJun on 2020/5/5.
 */
val applicationModule = module {
    single {
        UserLocalDataSource(MMKV.mmkvWithID(
                AndroidGenericFrameworkConfiguration.MMKV_ID,
                MMKV.SINGLE_PROCESS_MODE,
                AndroidGenericFrameworkConfiguration.MMKV_CRYPT_KEY
        ))
    }

    single { UserRemoteDataSource(get()) }

    single { RepositoryRemoteDataSource(get()) }
}

val networkModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder()
                .connectTimeout(AndroidGenericFrameworkConfiguration.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(AndroidGenericFrameworkConfiguration.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(BasicAuthInterceptor(get()))
                .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
                .client(get())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(String.format("%1\$s://%2\$s/", SCHEMA_HTTPS, AndroidGenericFrameworkConfiguration.HOST))
                .build()
    }
}

val repositoryModule = module {
    single { UserInfoRepository(get(), get()) }

    single { GitHubRepository(get()) }
}

val mainModule = module {
    scope<SplashActivity> {
        viewModel { SplashViewModel(get()) }
    }

    scope<MainActivity> {
        viewModel { MainViewModel(get()) }
    }
}

val userModule = module {
//    scope<LoginFragment> {
//        viewModel { LoginViewModel(get()) }
//    }
//
//    scope<LoginFragment2> {
//        viewModel { LoginViewModel(get()) }
//    }

    scope<PersonalCenterActivity> {
        viewModel { PersonalCenterViewModel(get()) }
    }
}

val loginInfoModule = module {
    scope<RegisterAndLoginActivity> {
        viewModel { LoginViewModel(get()) }
    }
}

val githubRepositoryModule = module {
    scope<RepositoryFragment> {
        viewModel { RepositoryViewModel(get()) }
    }
}

val applicationModules = listOf(
        applicationModule,
        networkModule,
        repositoryModule,
        mainModule,
        userModule,
        loginInfoModule,
        githubRepositoryModule
)

private const val SCHEMA_HTTPS = "https"