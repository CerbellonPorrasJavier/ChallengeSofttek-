package pe.javier.movieapp.domain.model

import pe.javier.movieapp.data.model.ListMovieModel
import java.io.Serializable

data class ListMovie(
    val page: Int = 1,
    val movies: List<Movie> = listOf(),
    val totalPages: Int = 1
): Serializable

fun ListMovieModel.toDomain() = ListMovie(page = page, movies = movies.map { it.toDomain() }, totalPages = totalPages)