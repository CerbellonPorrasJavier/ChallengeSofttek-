package pe.javier.movieapp.data.model

import pe.javier.movieapp.domain.model.Movie

data class SessionUiState(
    val isLoading: Boolean = false,
    val isLogin: Boolean = false,
    val currentMoviePage : Int = 1,
    val totalMoviePage: Int = 1,
    val selectedMovie: Movie? = null
)
