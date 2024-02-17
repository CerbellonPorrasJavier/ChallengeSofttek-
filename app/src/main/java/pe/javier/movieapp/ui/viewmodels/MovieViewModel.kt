package pe.javier.movieapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.javier.movieapp.data.model.MovieUiState
import pe.javier.movieapp.data.model.SessionUiState
import pe.javier.movieapp.domain.GetMovieListUseCase
import pe.javier.movieapp.domain.GetValidUsersUseCase
import pe.javier.movieapp.domain.model.Movie
import pe.javier.movieapp.domain.model.User
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getValidUsersUseCase: GetValidUsersUseCase,
    private val getMovieListUseCase: GetMovieListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SessionUiState())
    val uiState: StateFlow<SessionUiState> = _uiState.asStateFlow()

    var movieUiState: MovieUiState by mutableStateOf(MovieUiState.Loading)
        private set

    fun validateUserToLogin(user: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val validUsers = getValidUsersUseCase.invoke()
            val inputUser = User(user = user, password = password)
            if (validUsers.contains(inputUser)) {
                setIsLoggedIn()
            } else {
                setIsLoggedOut()
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun getMovies(page: Int = 1, apiKey: String) {
        viewModelScope.launch {
            movieUiState = MovieUiState.Loading
            setIsNotSelectedMovie()
            movieUiState = try {
                val movieList = getMovieListUseCase(page = page, apiKey = apiKey)
                _uiState.update { currentState ->
                    currentState.copy(
                        totalMoviePage = movieList.totalPages,
                        currentMoviePage = movieList.page
                    )
                }
                MovieUiState.Success(movies = movieList.movies)
            } catch (e: IOException) {
                MovieUiState.Error
            } catch (e: HttpException) {
                MovieUiState.Error
            }
        }
    }

    private fun setIsLoggedIn() {
        _uiState.update { currentState ->
            currentState.copy(isLogin = true)
        }
    }

    private fun setIsLoggedOut() {
        _uiState.update { currentState ->
            currentState.copy(isLogin = false)
        }
    }

    fun setSelectedMovie(movie: Movie) {
        _uiState.update { currentState ->
            currentState.copy(selectedMovie = movie)
        }
    }

    private fun setIsNotSelectedMovie() {
        _uiState.update { currentState ->
            currentState.copy(selectedMovie = null)
        }
    }
}