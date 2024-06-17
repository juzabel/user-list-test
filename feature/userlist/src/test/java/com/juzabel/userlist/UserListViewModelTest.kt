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
import com.juzabel.userlist.model.UserListState
import com.juzabel.userlist.viewModel.UserListViewModel
import com.juzabel.util.result.AResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.juzabel.errors.Error as JError

@RunWith(JUnit4::class)
class UserListViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineRule()
    private var viewModel: UserListViewModel? = null

    @MockK
    private lateinit var userDataSource: UserDataSource

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = UserListViewModel(userDataSource)
    }

    @After
    fun tearDown() {
        kotlinx.coroutines.Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Start`() = runTest {
        assertEquals(UserListState.START, viewModel?.state?.value)
    }

    @Test
    fun `get user list is Success`() = runTest {
        viewModel?.page?.value = 0
        val users = listOf(User("url1", "email1@email.com", "name1", 0, "lastname1"))
        coEvery { userDataSource.getUserList(1) } returns flowOf(AResult.Success(users))
        viewModel?.onNextPage()
        advanceUntilIdle()

        assertEquals(UserListState.SUCCESS, viewModel!!.state.value)
        assertEquals(users, viewModel!!.userList.value)
    }


    @Test
    fun `get user list is error`() = runTest {
        viewModel?.page?.value = 0
        val error = JError.Unknown("Unknown error")
        coEvery { userDataSource.getUserList(1) } returns flowOf(AResult.Failure(error))
        viewModel?.onNextPage()
        advanceUntilIdle()

        assertEquals(UserListState.ERROR, viewModel?.state?.value)
        assertEquals("Error message", viewModel?.errorToShow?.value)
    }

    @Test
    fun `get paginated list is correct`() = runTest {
        viewModel?.page?.value = 0
        val usersPage1 = listOf(User("url1", "email1@email.com", "name1", 0, "lastname1"))
        val usersPage2 = listOf(User("url2", "email2@email.com", "name2", 0, "lastname2"))
        coEvery { userDataSource.getUserList(1) } returns flowOf(AResult.Success(usersPage1))
        coEvery { userDataSource.getUserList(2) } returns flowOf(AResult.Success(usersPage2))
        viewModel?.onNextPage()
        advanceUntilIdle()
        viewModel?.onNextPage()
        advanceUntilIdle()

        assertEquals(3, viewModel?.page?.value) // Expected 3 because it starts from 1
        assertEquals(usersPage1 + usersPage2, viewModel?.userList?.value)
    }
}
