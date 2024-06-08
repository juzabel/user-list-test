package com.juzabel.common.viewmodel.user.datasource

import com.juzabel.common.viewmodel.user.model.User
import com.juzabel.errors.Error
import com.juzabel.util.result.AResult
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun getUserList(page: Int): Flow<AResult<List<User>, Error>>
    fun getUser(id: Long): Flow<AResult<User, Error>>
}
