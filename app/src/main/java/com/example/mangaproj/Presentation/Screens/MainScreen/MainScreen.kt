import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun MainScreen(navController: NavHostController, mangaList: List<Manga>) {
    val authViewModel: SignInViewModel = viewModel()
    val context = LocalContext.current
    val userState by authViewModel.userState

    // Состояние для поиска
    var searchQuery by remember { mutableStateOf("") }

    // Фильтруем список манги на основе поискового запроса
    val filteredMangaList = mangaList.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        authViewModel.isUserLoggedIn(context)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Доступная манга", color = Color.White) },
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
                // Поле для поиска
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Поиск манги") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF24b9bd),
                        unfocusedBorderColor = Color(0xFF24b9bd),
                        focusedLabelColor = Color(0xFF24b9bd),
                        unfocusedLabelColor = Color(0xFF24b9bd)
                    )
                )

                // Список манги
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredMangaList) { manga ->
                        MangaCardStyled(manga = manga) {
                            navController.navigate("mangaDetail/${manga.id}")
                        }
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
