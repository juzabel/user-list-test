package com.juzabel.userdetail

import com.juzabel.data.user.datasource.UserRemoteDataSourceImpl
import com.juzabel.userdetail.viewmodel.UserDetailViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val userDetailDi = module {
    viewModel { UserDetailViewModel(get(named<UserRemoteDataSourceImpl>())) }

    factory { Dispatchers.IO }
}
