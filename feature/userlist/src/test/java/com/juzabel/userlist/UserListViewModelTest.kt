package com.juzabel.userlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juzabel.common.viewmodel.user.datasource.UserDataSource
import com.juzabel.common.viewmodel.user.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import com.juzabel.userlist.model.UserListState
import com.juzabel.userlist.viewModel.UserListViewModel
import com.juzabel.util.result.AResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.rules.TestRule
import org.mockito.kotlin.whenever
import com.juzabel.errors.Error as JError

@ExperimentalCoroutinesApi
class UserListViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private var viewModel: UserListViewModel? = null

    @Mock
    private lateinit var userDataSource: UserDataSource

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    @Test
    fun `state should be START initially`() = testScope.runTest {
        viewModel = UserListViewModel(userDataSource, testDispatcher)
        assertEquals(UserListState.START, viewModel!!.state.value)
    }

    @Test
    fun `fetching user list successfully should emit SUCCESS state`() = testScope.runTest {
        viewModel = UserListViewModel(userDataSource, testDispatcher)

        val users = listOf(User("url1", "email1@email.com", "name1", 0, "lastname1"))
        whenever(userDataSource.getUserList(1)).thenReturn(flowOf(AResult.Success(users)))

        viewModel!!.page.value = 0
        viewModel!!.onNextPage()
        advanceUntilIdle()

        assertEquals(UserListState.SUCCESS, viewModel!!.state.value)
        assertEquals(users, viewModel!!.userList.value)
    }

    @Test
    fun `fetching user list with error should emit ERROR state`() = testScope.runTest {
        viewModel = UserListViewModel(userDataSource,testDispatcher)

        val error = JError.Unknown("Unknown error")
        whenever(userDataSource.getUserList(1)).thenReturn(flowOf(AResult.Failure(error)))

        viewModel!!.onNextPage()
        advanceUntilIdle()

        assertEquals(UserListState.ERROR, viewModel!!.state.value)
        assertEquals("Error message", viewModel!!.errorToShow.value)
    }

    @Test
    fun `onNextPage should increment page and fetch next page`() = testScope.runTest {
        viewModel = UserListViewModel(userDataSource, testDispatcher)

        val usersPage1 = listOf(User("url1", "email1@email.com", "name1", 0, "lastname1"))
        val usersPage2 = listOf(User("url2", "email2@email.com", "name2", 0, "lastname2"))

        whenever(userDataSource.getUserList(1)).thenReturn(flowOf(AResult.Success(usersPage1)))
        whenever(userDataSource.getUserList(2)).thenReturn(flowOf(AResult.Success(usersPage2)))

        viewModel!!.onNextPage()
        advanceUntilIdle()
        viewModel!!.onNextPage()
        advanceUntilIdle()

        assertEquals(3, viewModel!!.page.value) // Expected 3 because it starts from 1
        assertEquals(usersPage1 + usersPage2, viewModel!!.userList.value)
    }
}
