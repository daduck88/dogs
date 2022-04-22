package com.duck.dogsapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.duck.dogsapp.data.remote.objects.Breed
import com.duck.dogsapp.ui.breeds.BreedsViewModel
import com.duck.dogsapp.ui.theme.DogsAppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun BreedsScreen(
    viewModel: BreedsViewModel = hiltViewModel(),
    navController: NavHostController
) {
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
                        title = { Text("Breeds") }
                    )
                }
            ) {
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        viewModel.fetchBreeds() },
                ) {
                    BreedList(
                        viewModel.uiState.breedsItems,
                        navController,
                        viewModel.uiState.isLoading
                    )
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
fun BreedList(breeds: List<Breed>, navController: NavHostController, isLoading: Boolean) {
    if (breeds.isEmpty()) {
        if(isLoading){
            Emtpy()
        } else {
            TryAgain()
        }
    } else {
        LazyColumn {
            items(breeds.size) {
                BreedCard(breed = breeds[it], navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BreedCard(breed: Breed, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        onClick = { navController.navigate(Screen.Breed.createRoute(breedId = breed.name)) }
    ) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = breed.name,
                style = MaterialTheme.typography.h6
            )
        }
    }
}