import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.mangaproj.Presentation.Components.BasicBlueButton
import com.example.mangaproj.Presentation.Components.LoadingComponent
import com.example.mangaproj.Presentation.Components.MangaCardStyled
import com.example.mangaproj.Presentation.Navigation.NavigationRoutes
import com.example.mangaproj.Presentation.ViewModels.SignInViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val authViewModel: SignInViewModel = viewModel()
    val mangaViewModel: MangaViewModel = viewModel()
    val context = LocalContext.current
    val userState by authViewModel.userState
    val mangaList by mangaViewModel.mangaList.collectAsState()

    LaunchedEffect(Unit) {
        authViewModel.isUserLoggedIn(context)
        mangaViewModel.loadManga()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Манга", color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF24b9bd)
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(mangaList) { manga ->
                        MangaCardStyled(manga = manga)
                    }
                }

                BasicBlueButton(
                    buttonText = "Выйти",
                    onClick = {
                        authViewModel.signOut(context)
                        navController.navigate(NavigationRoutes.SIGNIN) {
                            popUpTo(0)
                        }
                    }
                )
            }
        }
    )
}