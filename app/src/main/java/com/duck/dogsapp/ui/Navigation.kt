package com.duck.dogsapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun DogApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.Breeds.route) {
        composable(route = Screen.Breeds.route) {
            BreedsScreen(navController = navController)
        }
        composable(route = Screen.Breed.route) { backStackEntry ->
            val breedId = backStackEntry.arguments?.getString("breedId")
            requireNotNull(breedId) { "breedId parameter wasn't found. Please make sure it's set!" }
            BreedScreen(breedId)
        }
    }
}

sealed class Screen(val route: String) {
    object Breeds : Screen("breeds")
    object Breed : Screen("breed/{breedId}") {
        fun createRoute(breedId: String) = "breed/$breedId"
    }
}