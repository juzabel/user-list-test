package com.juzabel.testapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.juzabel.testapp.viewModel.datasource.TestDataSource

class TestViewModel(
    private val localDataSource: TestDataSource,
    private val remoteDataSource: TestDataSource
) : ViewModel() {

    fun test() {
        Log.d("TAG", "${localDataSource.getAll()} - ${remoteDataSource.getAll()}")
    }
}
