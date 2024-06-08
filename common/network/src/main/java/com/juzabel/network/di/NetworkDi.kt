package com.juzabel.network.di

import com.juzabel.network.BuildConfig
import com.juzabel.network.services.UserService
import com.juzabel.network.session.SessionManager
import com.juzabel.network.util.RetrofitUtils
import com.juzabel.util.config.CommonConfig
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit

val networkDi = module {
    single<UserService> {
        RetrofitUtils.createService(
            get<Retrofit>(parameters = { parametersOf(get<CommonConfig>()) }),
            UserService::class.java
        )
    }
    single<Retrofit> { (config: CommonConfig) ->
        RetrofitUtils.make(
            get(),
            config.url,
            BuildConfig.DEBUG
        )
    }
    singleOf(::SessionManager)
}
