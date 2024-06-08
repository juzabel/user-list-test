package com.juzabel.data.user.mapper

import com.juzabel.common.viewmodel.user.model.User
import com.juzabel.network.models.UserRemote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun UserRemote.mapToDomain() = User(avatar, email, firstName, id, lastName)
fun List<UserRemote>.mapToDomain() = map { it.mapToDomain() }
fun Flow<List<UserRemote>>.mapToDomain() = map { it.mapToDomain() }
