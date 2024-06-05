package com.juzabel.testapp.viewModel.datasource

import com.juzabel.testapp.viewModel.model.TestDomain
import kotlinx.coroutines.flow.Flow

interface TestDataSource {
    suspend fun insert(test: TestDomain)

    fun getAll(): Flow<List<TestDomain>>

    suspend fun update(test: TestDomain)

    suspend fun delete(test: TestDomain)
}
