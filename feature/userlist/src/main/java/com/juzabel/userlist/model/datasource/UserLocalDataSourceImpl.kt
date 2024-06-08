package com.juzabel.userlist.model.datasource

import com.juzabel.errors.Error
import com.juzabel.userlist.viewModel.datasource.UserDataSource
import com.juzabel.userlist.viewModel.model.User
import com.juzabel.util.result.AResult
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

class UserLocalDataSourceImpl : UserDataSource, KoinComponent {
    override fun getUserList(page: Int): Flow<AResult<List<User>, Error>> {
        TODO()
    }

    override fun getUser(id: Long): Flow<AResult<User, Error>> {
        TODO()
    }
}
