package com.juzabel.userlist.model.datasource

import com.juzabel.errors.Error
import com.juzabel.network.models.exceptions.UnknownException
import com.juzabel.network.services.UserService
import com.juzabel.userlist.model.mapper.mapToDomain
import com.juzabel.userlist.viewModel.datasource.UserDataSource
import com.juzabel.userlist.viewModel.model.User
import com.juzabel.util.result.AResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent

class UserRemoteDataSourceImpl(
    private val service: UserService,
    private val backgroundDispatcher: CoroutineDispatcher
) : UserDataSource, KoinComponent {
    override fun getUserList(page: Int) = flow<AResult<List<User>, Error>> {
        try {
            with(service.getUserList(page)) {
                emit(AResult.Success(data.mapToDomain()))
            }
        } catch (e: UnknownException) {
            e.printStackTrace()
            emit(AResult.Failure(Error.Unknown("Unknown error")))
        }
    }.flowOn(backgroundDispatcher)

    override fun getUser(id: Long) = flow<AResult<User, Error>> {
        try {
            with(service.getUser(id)) {
                emit(AResult.Success(data.mapToDomain()))
            }
        } catch (e: UnknownException) {
            e.printStackTrace()
            emit(AResult.Failure(Error.Unknown("Unknown error")))
        }
    }.flowOn(backgroundDispatcher)
}
