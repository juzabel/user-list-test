package com.juzabel.userlist

import com.juzabel.network.services.UserService
import com.juzabel.userlist.model.datasource.UserLocalDataSourceImpl
import com.juzabel.userlist.model.datasource.UserRemoteDataSourceImpl
import com.juzabel.userlist.viewModel.UserListViewModel
import com.juzabel.userlist.viewModel.datasource.UserDataSource
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val testDi = module {
    factory<UserDataSource>(named<UserLocalDataSourceImpl>()) { UserLocalDataSourceImpl() }
    factory<UserDataSource>(named<UserRemoteDataSourceImpl>()) { UserRemoteDataSourceImpl(get<UserService>(), get()) }
    viewModel { UserListViewModel(get(named<UserRemoteDataSourceImpl>())) }

    factory { Dispatchers.IO }
}
