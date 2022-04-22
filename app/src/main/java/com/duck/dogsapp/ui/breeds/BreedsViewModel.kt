package com.duck.dogsapp.ui.breeds

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duck.dogsapp.data.BreedsRepository
import com.duck.dogsapp.data.remote.objects.Breed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(private val repository: BreedsRepository) : ViewModel() {
    var uiState by mutableStateOf(BreedsUiState(isLoading = true))
        private set

    private var fetchJob: Job? = null
    private var responseCount = 0

    init {
        fetchBreeds()
    }

    fun fetchBreeds() {
        fetchJob?.cancel()
        uiState = uiState.copy(
            isLoading = true,
            errorMessage = null
        )
        responseCount = 0
        fetchJob = viewModelScope.launch {
            repository.getBreeds().catch {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Something when wrong"
                )
            }.collect { breeds ->
                responseCount++
                val isLoading = responseCount != 2
                uiState = BreedsUiState(
                    breedsItems = breeds,
                    isLoading = isLoading,
                    errorMessage = null
                )
            }
        }
    }

}

data class BreedsUiState(
    val breedsItems: List<Breed> = listOf(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
