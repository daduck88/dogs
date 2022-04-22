package com.duck.dogsapp.ui.breeds

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.duck.dogsapp.data.BreedsRepository
import com.duck.dogsapp.data.remote.objects.BreedTest.DOGO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class BreedsViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockRepository: BreedsRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `viewModel init loading`() = runTest {
        Mockito.`when`(mockRepository.getBreeds()).thenReturn(
            flow {})
        val viewModel = BreedsViewModel(mockRepository)
        delay(10)
        assertThat(
            BreedsUiState(isLoading = true).isLoading,
            IsEqual.equalTo(viewModel.uiState.isLoading)
        )
    }

    @Test
    fun `viewModel first response still loading`() = runTest {
        val listDogs = listOf(DOGO)
        val breedsUiState = BreedsUiState(breedsItems = listDogs)

        Mockito.`when`(mockRepository.getBreeds()).thenReturn(
            flow {
                emit(listDogs)})
        val viewModel = BreedsViewModel(mockRepository)
        delay(10)
        assertThat(
            BreedsUiState(isLoading = true).isLoading,
            IsEqual.equalTo(viewModel.uiState.isLoading)
        )
        assertThat(
            breedsUiState.breedsItems[0],
            IsEqual.equalTo(viewModel.uiState.breedsItems[0])
        )
    }

    @Test
    fun `viewModel second response not loading`() = runTest {
        val listDogs = listOf(DOGO)
        val breedsUiState = BreedsUiState(breedsItems = listDogs)

        Mockito.`when`(mockRepository.getBreeds()).thenReturn(
            flow {
                emit(listDogs)
                emit(listDogs)
            })
        val viewModel = BreedsViewModel(mockRepository)
        delay(10)
        assertThat(
            BreedsUiState(isLoading = false).isLoading,
            IsEqual.equalTo(viewModel.uiState.isLoading)
        )
        assertThat(
            breedsUiState.breedsItems[0],
            IsEqual.equalTo(viewModel.uiState.breedsItems[0])
        )
    }
}