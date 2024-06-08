package com.juzabel.userlist.model.mapper

import com.juzabel.network.models.UserRemote
import com.juzabel.userlist.viewModel.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun UserRemote.mapToDomain() = User(avatar, email, firstName, id, lastName)
fun List<UserRemote>.mapToDomain() = map { it.mapToDomain() }
fun Flow<List<UserRemote>>.mapToDomain() = map { it.mapToDomain() }
