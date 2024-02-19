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
import pe.javier.movieapp.data.model.SessionUiState
import pe.javier.movieapp.domain.ClearDataUseCase
import pe.javier.movieapp.domain.GetIfExistsIntoDatabase
import pe.javier.movieapp.domain.GetMovieListUseCase
import pe.javier.movieapp.domain.GetValidUserUseCase
import pe.javier.movieapp.domain.InsertUserIntoDatabase
import pe.javier.movieapp.domain.model.Movie
import pe.javier.movieapp.domain.model.User
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getValidUserUseCase: GetValidUserUseCase,
    private val getMovieListUseCase: GetMovieListUseCase,
    private val insertUserIntoDatabase: InsertUserIntoDatabase,
    private val getIfExistsIntoDatabase: GetIfExistsIntoDatabase,
    private val clearDataUseCase: ClearDataUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SessionUiState())
    val uiState: StateFlow<SessionUiState> = _uiState.asStateFlow()

    var movieUiState: MovieUiState by mutableStateOf(MovieUiState.Inactive)
        private set

    init {
        validateIsLoggedIn()
    }

    private fun validateIsLoggedIn() {
        viewModelScope.launch {
            val isLoggedIn = getIfExistsIntoDatabase()
            _uiState.update { currentState ->
                currentState.copy(isLogin = isLoggedIn)
            }
        }
    }

    fun validateUserToLogin(user: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val validUser = getValidUserUseCase()
            val inputUser = User(user = user, password = password)
            if (validUser == inputUser) {
                setIsLoggedIn(inputUser)
            } else {
                setIsLoggedOut()
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun getMovies(page: Int = 1) {
        viewModelScope.launch {
            movieUiState = MovieUiState.Loading
            movieUiState = try {
                val movieList = getMovieListUseCase(page = page)
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

    private fun setIsLoggedIn(inputUser: User) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(isLogin = true)
            }
            insertUserIntoDatabase(inputUser)
        }
    }

    fun setIsLoggedOut() {
        viewModelScope.launch {
            resetUiState()
            clearDataUseCase()
        }
    }

    private fun resetUiState() {
        _uiState.value = SessionUiState()
    }

    fun setSelectedMovie(movie: Movie) {
        _uiState.update { currentState ->
            currentState.copy(selectedMovie = movie)
        }
    }
}

sealed interface MovieUiState {
    object Inactive : MovieUiState
    data class Success(val movies: List<Movie>) : MovieUiState
    object Error : MovieUiState
    object Loading : MovieUiState
}