package com.juzabel.testapp.model.mapper

import com.juzabel.local.models.Test
import com.juzabel.testapp.viewModel.model.TestDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Test.mapToDomain() = TestDomain(id, text)
fun List<Test>.mapToDomain() = map { it.mapToDomain() }
fun Flow<List<Test>>.mapToDomain() = map { it.mapToDomain() }
