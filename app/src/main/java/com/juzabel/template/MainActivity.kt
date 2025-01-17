package com.juzabel.template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.juzabel.template.ui.theme.CurrentDownloadsTheme
import com.juzabel.userdetail.view.UserDetailScreen
import com.juzabel.userdetail.view.UserDetailScreenDestination
import com.juzabel.userlist.view.UserListScreen
import com.juzabel.userlist.view.UserListScreenDestination
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrentDownloadsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    ContentContainer()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentContainer() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                colors =
                    topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                title = {
                    Text(stringResource(id = R.string.app_name))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Content(navController, innerPadding)
    }
}

@Composable
fun Content(
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    NavHost(
        modifier = Modifier.padding(innerPadding),
        navController = navController,
        startDestination = UserListScreenDestination,
        enterTransition = { slideInHorizontally(tween(DURATION)) },
        exitTransition = { slideOutHorizontally(tween(DURATION)) },
    ) {
        composable(UserListScreenDestination) {
            UserListScreen(viewModel = koinViewModel(), onNavigate = {
                navController.navigate(UserDetailScreenDestination.replace("{user_id}", it.toString()))
            })
        }
        composable(
            UserDetailScreenDestination,
            arguments =
                listOf(
                    navArgument("user_id") {
                        type = NavType.LongType
                    },
                ),
        ) {
            val userId = it.arguments?.getLong("user_id")
            UserDetailScreen(viewModel = koinViewModel(), userId = userId)
        }
    }
}

private const val DURATION = 200
