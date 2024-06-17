package com.juzabel.userlist

import com.juzabel.common.viewmodel.user.datasource.UserDataSource
import com.juzabel.data.user.datasource.UserRemoteDataSourceImpl
import com.juzabel.network.services.UserService
import com.juzabel.userlist.viewModel.UserListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val userListDi = module {
    factory<UserDataSource>(named<UserRemoteDataSourceImpl>()) {
        UserRemoteDataSourceImpl(
            get<UserService>(),
            get()
        )
    }
    viewModel { UserListViewModel(get(named<UserRemoteDataSourceImpl>())) }

    factory { Dispatchers.IO }
}
