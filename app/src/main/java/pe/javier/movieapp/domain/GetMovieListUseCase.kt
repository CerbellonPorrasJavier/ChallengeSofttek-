package pe.javier.movieapp.domain

import pe.javier.movieapp.data.MovieAppRepository
import pe.javier.movieapp.data.database.entities.toDatabase
import pe.javier.movieapp.domain.model.ListMovie
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(
    private val repository: MovieAppRepository
) {
    suspend operator fun invoke(page: Int = 1): ListMovie {
        val movieList = repository.getAllMoviesFromApi(page = page)
        return if (movieList?.movies != null && movieList.movies.isNotEmpty()) {
            repository.insertMovies(movieList.movies.map { it.toDatabase() })
            movieList
        } else {
            ListMovie(movies = repository.getAllMoviesFromDatabase().takeLast(20))
        }
    }
}