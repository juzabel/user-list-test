package com.juzabel.network.di

import com.juzabel.network.BuildConfig
import com.juzabel.network.services.SampleService
import com.juzabel.network.session.SessionManager
import com.juzabel.network.util.RetrofitUtils
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit

val networkDi = module {
    single { (url: String) ->
        RetrofitUtils.createService(
            get(parameters = { parametersOf(url) }),
            SampleService::class.java
        )
    }
    single<Retrofit> { (url: String) ->
        RetrofitUtils.make(
            get(),
            url,
            BuildConfig.DEBUG
        )
    }
    singleOf(::SessionManager)
}
