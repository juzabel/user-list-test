package com.juzabel.userdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juzabel.common.viewmodel.user.datasource.UserDataSource
import com.juzabel.common.viewmodel.user.model.User
import com.juzabel.errors.Error
import com.juzabel.userdetail.model.UserDetailState
import com.juzabel.util.result.AResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class UserDetailViewModel(
    private val remoteDataSource: UserDataSource
) : ViewModel() {
    private val _state: MutableStateFlow<UserDetailState> = MutableStateFlow(UserDetailState.START)
    val state = _state.asStateFlow()

    private val _userDetail: MutableStateFlow<User?> = MutableStateFlow(null)
    val userDetail = _userDetail.asStateFlow()

    private val _error: MutableStateFlow<Error?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    fun onLoad(userId: Long?) {
        viewModelScope.launch {
            _state.value = UserDetailState.LOADING
            if (userId == null) {
                _error.value = Error.Unknown("")
                _state.value = UserDetailState.ERROR
            } else {
                remoteDataSource.getUser(userId).collect { result ->
                    when (result) {
                        is AResult.Success -> {
                            _userDetail.value = result.value
                            _state.value = UserDetailState.SUCCESS
                        }

                        is AResult.Failure -> {
                            _error.value = result.error
                            _state.value = UserDetailState.ERROR
                        }
                    }
                }
            }
        }
    }
}
