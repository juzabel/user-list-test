package com.juzabel.userlist.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.juzabel.userlist.model.UserListState
import com.juzabel.userlist.viewModel.UserListViewModel
import com.juzabel.userlist.viewModel.model.User

const val UserListScreenDestination = "UserListScreenDestination"

@Composable
fun UserListScreen(viewModel: UserListViewModel) {
    val userList by viewModel.userList.collectAsState()
    val state by viewModel.state.collectAsState()
    val error by viewModel.errorToShow.collectAsState()
    val endScroll by viewModel.endScroll.collectAsState()
    val nextPageAction: () -> Unit = {
        viewModel.onNextPage()
    }
    val itemClicked: (id: Long) -> Unit = {
    }

    when (state) {
        UserListState.START, UserListState.LOADING -> Loading()
        UserListState.ERROR -> ErrorMessage(error)
        UserListState.SUCCESS -> UserList(
            userList = userList,
            endScroll = endScroll,
            nextPageAction = nextPageAction,
            itemClicked = itemClicked
        )
    }
}

@Composable
fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun ErrorMessage(error: String?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = com.juzabel.resources.R.string.error_message) + " " + error,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun UserList(
    userList: List<User>,
    endScroll: Boolean,
    nextPageAction: () -> Unit,
    itemClicked: (id: Long) -> Unit
) {
    val scrollState = rememberLazyListState()
    val fetchNextPage: Boolean by remember {
        derivedStateOf {
            val count = userList.size
            val lastVisibleItem = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: return@derivedStateOf false
            return@derivedStateOf lastVisibleItem >= count - 1
        }
    }

    LazyColumn(state = scrollState) {
        items(userList.size) { position ->
            UserItem(user = userList[position], itemClicked)
        }
    }
    if (fetchNextPage && !endScroll) {
        nextPageAction()
    }
}

@Composable
fun UserItem(user: User, itemClicked: (id: Long) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .clickable { itemClicked(user.id) },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .padding(8.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillWidth,
                    model = user.avatar,
                    contentDescription = "avatar",
                    placeholder = painterResource(id = com.juzabel.resources.R.drawable.placeholder)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                UserName(firstName = user.firstName, lastName = user.lastName)
            }
        }
    }
}

@Composable
fun UserName(firstName: String, lastName: String) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .width(148.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NameText(modifier = Modifier.align(Alignment.Start), name = firstName)
        NameText(modifier = Modifier.align(Alignment.Start), name = lastName)
    }
}

@Composable
fun NameText(modifier: Modifier = Modifier, name: String) {
    Text(
        modifier = modifier,
        text = name,
        style = MaterialTheme.typography.titleLarge
    )
}

@Preview
@Composable
fun UserListPreview() {
    UserList(getSampleList(), false, {}, { 0 })
}

private fun getSampleList(): List<User> {
    return arrayListOf(
        User("url", "email@email.com", "Jules", 0, "Winter"),
        User("url", "email2@email.com", "Paula", 0, "Spring")
    )
}
