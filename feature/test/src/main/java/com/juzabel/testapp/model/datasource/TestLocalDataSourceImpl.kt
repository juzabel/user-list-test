package com.juzabel.testapp.model.datasource

import com.juzabel.local.AppDatabase
import com.juzabel.testapp.model.mapper.mapToDomain
import com.juzabel.testapp.viewModel.datasource.TestDataSource
import com.juzabel.testapp.viewModel.model.TestDomain
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

class TestLocalDataSourceImpl(private val appDatabase: AppDatabase) : TestDataSource, KoinComponent {
    override suspend fun insert(test: TestDomain) = Unit

    override fun getAll(): Flow<List<TestDomain>> =
        appDatabase.testDao().gelAll().mapToDomain()

    override suspend fun update(test: TestDomain) = Unit

    override suspend fun delete(test: TestDomain) = Unit
}
