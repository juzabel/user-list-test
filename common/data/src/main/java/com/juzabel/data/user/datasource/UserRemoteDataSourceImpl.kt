package com.juzabel.data.user.datasource

import com.juzabel.common.viewmodel.user.datasource.UserDataSource
import com.juzabel.common.viewmodel.user.model.User
import com.juzabel.data.user.mapper.mapToDomain
import com.juzabel.errors.Error
import com.juzabel.network.services.UserService
import com.juzabel.util.result.AResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent
import java.net.UnknownHostException

class UserRemoteDataSourceImpl(
    private val service: UserService,
    private val backgroundDispatcher: CoroutineDispatcher
) : UserDataSource, KoinComponent {
    override fun getUserList(page: Int) = flow<AResult<List<User>, Error>> {
        try {
            with(service.getUserList(page)) {
                emit(AResult.Success(data.mapToDomain()))
            }
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            emit(AResult.Failure(Error.Unknown("No network")))
        }
    }.flowOn(backgroundDispatcher)

    override fun getUser(id: Long) = flow<AResult<User, Error>> {
        try {
            with(service.getUser(id)) {
                emit(AResult.Success(data.mapToDomain()))
            }
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            emit(AResult.Failure(Error.Unknown("No network")))
        }
    }.flowOn(backgroundDispatcher)
}
