package pe.javier.movieapp.data.model

import pe.javier.movieapp.domain.model.Movie

sealed interface MovieUiState {
    data class Success(val movies: List<Movie>) : MovieUiState
    object Error : MovieUiState
    object Loading : MovieUiState
}