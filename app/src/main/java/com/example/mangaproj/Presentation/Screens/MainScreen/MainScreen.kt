import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mangaproj.Domain.Models.UserState
import com.example.mangaproj.Presentation.Components.LoadingComponent
import com.example.mangaproj.Presentation.Navigation.NavigationRoutes
import com.example.mangaproj.Presentation.ViewModels.SupabaseAuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val authViewModel: SupabaseAuthViewModel = viewModel()
    val context = LocalContext.current
    val userState by authViewModel.userState
    var currentUserState by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        authViewModel.isUserLoggedIn(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Моя манга",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF24b9bd),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(
                        onClick = {
                            authViewModel.signOut(context)
                            navController.navigate(NavigationRoutes.SIGNIN) {
                                popUpTo(0)
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Выход",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (userState) {
                is UserState.Loading -> {
                    LoadingComponent()
                }
                is UserState.Success -> {
                    MangaScreen(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(8.dp)
                    )
                }
                is UserState.Error -> {
                    val message = (userState as UserState.Error).message
                    currentUserState = message

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Ошибка: $currentUserState",
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Button(
                            onClick = { authViewModel.isUserLoggedIn(context) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF24b9bd)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp, vertical = 8.dp)
                        ) {
                            Text("Попробовать снова")
                        }

                        OutlinedButton(
                            onClick = {
                                authViewModel.signOut(context)
                                navController.navigate(NavigationRoutes.SIGNIN) {
                                    popUpTo(0)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp, vertical = 8.dp)
                        ) {
                            Text("Выйти")
                        }
                    }
                }
            }
        }
    }
}