package com.juzabel.userlist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juzabel.common.viewmodel.user.datasource.UserDataSource
import com.juzabel.common.viewmodel.user.model.User
import com.juzabel.errors.Error
import com.juzabel.userlist.model.UserListState
import com.juzabel.util.result.AResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModel(
    private val remoteDataSource: UserDataSource,
) : ViewModel() {

    private val _state: MutableStateFlow<UserListState> = MutableStateFlow(UserListState.START)
    val state = _state.asStateFlow()

    private val _lastUserList: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())

    private val _endScroll = MutableStateFlow<Boolean>(false)

    val page: MutableStateFlow<Int> = MutableStateFlow(1)

    private val _stateResult: StateFlow<AResult<List<User>, Error>> =
        page.onStart { _state.emit(UserListState.LOADING) }
            .flatMapMerge {
                remoteDataSource.getUserList(page.value)
            }
            .onEach {
                if (it is AResult.Success) {
                    _state.emit(UserListState.SUCCESS)
                } else {
                    _state.emit(UserListState.ERROR)
                }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), AResult.Success(emptyList()))

    val errorToShow: StateFlow<String?> = _stateResult.map {
        if (it is AResult.Failure) it.error.message else null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    val userList: StateFlow<List<com.juzabel.common.viewmodel.user.model.User>> = _stateResult.map {

        val success = (it is AResult.Success)

        val list = if (it is AResult.Success) it.value else emptyList()

        val ret = if (list.isEmpty() && success) {
            _endScroll.emit(true)
            _lastUserList.value.toMutableList()
        } else {
            _endScroll.emit(false)
            _lastUserList.value.toMutableList() + list
        }
        _lastUserList.emit(ret)

        ret
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun onNextPage() {
        if (!_endScroll.value) {
            viewModelScope.launch {
                page.emit(page.value + 1)
            }
        }
    }
}
