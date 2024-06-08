package com.juzabel.data.user

import com.juzabel.common.viewmodel.user.datasource.UserDataSource
import com.juzabel.data.user.datasource.UserRemoteDataSourceImpl
import com.juzabel.network.services.UserService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val userDi = module {
    factory<UserDataSource>(named<UserRemoteDataSourceImpl>()) {
        UserRemoteDataSourceImpl(
            get<UserService>(),
            get()
        )
    }
}
