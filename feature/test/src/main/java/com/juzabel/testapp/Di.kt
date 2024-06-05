package com.juzabel.testapp

import com.juzabel.testapp.model.datasource.TestLocalDataSourceImpl
import com.juzabel.testapp.model.datasource.TestRemoteDataSourceImpl
import com.juzabel.testapp.viewModel.TestViewModel
import com.juzabel.testapp.viewModel.datasource.TestDataSource
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val testDi = module {
    factory<TestDataSource>(named<TestLocalDataSourceImpl>()) { TestLocalDataSourceImpl(get()) }
    factory<TestDataSource>(named<TestRemoteDataSourceImpl>()) { TestRemoteDataSourceImpl() }
    viewModel { TestViewModel(get(named<TestLocalDataSourceImpl>()), get(named<TestRemoteDataSourceImpl>())) }
}
