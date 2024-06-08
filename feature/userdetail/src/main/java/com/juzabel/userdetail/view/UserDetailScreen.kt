package com.juzabel.userdetail.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.juzabel.common.viewmodel.user.model.User
import com.juzabel.composables.ErrorMessage
import com.juzabel.composables.Loading
import com.juzabel.resources.R
import com.juzabel.userdetail.model.UserDetailState
import com.juzabel.userdetail.viewmodel.UserDetailViewModel

const val UserDetailScreenDestination = "UserDetailScreenDestination/{user_id}"

@Composable
fun UserDetailScreen(viewModel: UserDetailViewModel, userId: Long?) {
    val userDetail by viewModel.userDetail.collectAsState()
    val state by viewModel.state.collectAsState()
    val error by viewModel.error.collectAsState()

    when (state) {
        UserDetailState.START -> viewModel.onLoad(userId)
        UserDetailState.LOADING -> Loading()
        UserDetailState.ERROR -> ErrorMessage(error?.message)
        UserDetailState.SUCCESS -> {
            if (userDetail != null) {
                UserDetail(userDetail = userDetail!!)
            } else {
                ErrorMessage()
            }
        }
    }
}

@Composable
fun UserDetail(userDetail: User) {
    Card(
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .padding(8.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillWidth,
                    model = userDetail.avatar,
                    contentDescription = "avatar",
                    placeholder = painterResource(id = R.drawable.placeholder)
                )
            }

            Text(
                text = stringResource(id = com.juzabel.resources.R.string.detail_id) + " ${userDetail.id}",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = stringResource(id = com.juzabel.resources.R.string.detail_name) +
                    " ${userDetail.firstName} ${userDetail.lastName}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = com.juzabel.resources.R.string.detail_email) + " ${userDetail.email}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview
@Composable
fun UserPreview() {
    UserDetail(userDetail = getSampleUser())
}

fun getSampleUser(): User = User("url", "email@email.com", "name", 0, "lastname")
