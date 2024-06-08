package com.juzabel.userlist.viewModel.datasource

import com.juzabel.errors.Error
import com.juzabel.userlist.viewModel.model.User
import com.juzabel.util.result.AResult
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun getUserList(page: Int): Flow<AResult<List<User>, Error>>
    fun getUser(id: Long): Flow<AResult<User, Error>>
}
