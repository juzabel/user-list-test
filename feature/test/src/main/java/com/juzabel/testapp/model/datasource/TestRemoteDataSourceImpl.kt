package com.juzabel.testapp.model.datasource

import com.juzabel.network.services.SampleService
import com.juzabel.testapp.model.mapper.mapToDomain
import com.juzabel.testapp.viewModel.datasource.TestDataSource
import com.juzabel.testapp.viewModel.model.TestDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class TestRemoteDataSourceImpl : TestDataSource, KoinComponent {
    private val service: SampleService by inject { parametersOf("https://url.test") }
    override suspend fun insert(test: TestDomain) {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<TestDomain>> = flow {
        emit(service.getTests().mapToDomain())
    }

    override suspend fun update(test: TestDomain) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(test: TestDomain) {
        TODO("Not yet implemented")
    }
}
