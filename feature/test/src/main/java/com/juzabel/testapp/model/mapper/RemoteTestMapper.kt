package com.juzabel.testapp.model.mapper

import com.juzabel.network.models.RemoteTest
import com.juzabel.testapp.viewModel.model.TestDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun RemoteTest.mapToDomain() = TestDomain(id, text)
fun List<RemoteTest>.mapToDomain() = map { it.mapToDomain() }
fun Flow<List<RemoteTest>>.mapToDomain() = map { it.mapToDomain() }
