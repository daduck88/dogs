package com.duck.dogsapp.ui.breeds

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duck.dogsapp.data.BreedImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedViewModel @Inject constructor(
    private val repository: BreedImagesRepository
    ) : ViewModel() {
    var uiState by mutableStateOf(BreedUiState(isLoading = true))
        private set


    private var fetchJob: Job? = null
    private var responseCount = 0

    fun fetchBreed(breed: String) {
        if(fetchJob?.isActive == true){
            return
        }
        uiState = uiState.copy(
            isLoading = true,
            errorMessage = null
        )
        responseCount = 0
        fetchJob = viewModelScope.launch {
            repository.getBreedImages(breed).catch {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Something when wrong"
                )
            }.collect { breedImages ->
                responseCount++
                val isLoading = responseCount != 2
                uiState = BreedUiState(breedItems = breedImages,
                    isLoading = isLoading,
                    errorMessage = null)
                if(!isLoading){
                    fetchJob?.cancel()
                }
            }
        }
    }
}

data class BreedUiState(
    val breedItems: List<String> = listOf(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)