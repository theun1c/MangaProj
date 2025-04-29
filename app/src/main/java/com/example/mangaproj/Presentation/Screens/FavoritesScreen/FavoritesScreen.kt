package com.example.mangaproj.Presentation.Screens.FavoritesScreen

import Manga
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mangaproj.Presentation.Components.MangaCardStyled

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavHostController,
    favoriteMangaList: State<List<Manga>>,
    onFavoriteClick: (Manga) -> Unit, // Передача onFavoriteClick
    onMangaClick: (Manga) -> Unit // Передача onMangaClick
) {
    val mangaList = favoriteMangaList.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Избранное", color = Color.White)
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Назад",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF24b9bd)
                )
            )
        },
        content = { paddingValues ->
            if (mangaList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Пока нет избранной манги", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(mangaList) { manga ->
                        MangaCardStyled(
                            manga = manga,
                            onMangaClick = onMangaClick, // Передача onMangaClick
                            onFavoriteClick = onFavoriteClick // Передача onFavoriteClick
                        )
                    }
                }
            }
        }
    )
}
