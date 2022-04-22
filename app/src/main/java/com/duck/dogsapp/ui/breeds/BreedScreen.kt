package com.duck.dogsapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.duck.dogsapp.ui.breeds.BreedViewModel
import com.duck.dogsapp.ui.theme.DogsAppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@Composable
fun BreedScreen(
    breed: String,
    viewModel: BreedViewModel = hiltViewModel()
) {
    viewModel.fetchBreed(breed)
    DogsAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            val swipeRefreshState = rememberSwipeRefreshState(viewModel.uiState.isLoading)
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(
                        title = { Text(breed) }
                    )
                }) {

                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        viewModel.fetchBreed(breed)
                                },
                ) {
                    DogsList(viewModel.uiState.breedItems, viewModel.uiState.isLoading)
                }
                viewModel.uiState.errorMessage?.let {
                    scope.launch {
                        scaffoldState.snackbarHostState
                            .showSnackbar(it)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DogsList(breedDogs: List<String>, isLoading: Boolean) {
    if (breedDogs.isEmpty()) {
        if(isLoading){
            Emtpy()
        } else {
            TryAgain()
        }
    } else {
        LazyVerticalGrid(
            cells = GridCells.Adaptive(minSize = 150.dp)
        ) {
            items(breedDogs.size) {
                val dog = breedDogs[it]
                DogCard(dog)
            }
        }
    }
}

@Composable
fun DogCard(dog: String) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            GlideImage(
                modifier = Modifier.size(192.dp, 128.dp),
                imageModel = dog,
                contentScale = ContentScale.Inside,
                circularReveal = CircularReveal(duration = 350),
                failure = {
                    Text(text = "image request failed.")
                }
            )
        }
    }
}