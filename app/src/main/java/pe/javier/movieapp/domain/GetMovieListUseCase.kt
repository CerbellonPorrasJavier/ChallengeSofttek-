package pe.javier.movieapp.domain

import pe.javier.movieapp.data.MovieRepository
import pe.javier.movieapp.data.database.entities.toDatabase
import pe.javier.movieapp.domain.model.ListMovie
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int = 1, apiKey: String): ListMovie {
        val movieList = repository.getAllMoviesFromApi(page = page, apiKey = apiKey)
        return if (movieList?.movies != null && movieList.movies.isEmpty()) {
            repository.insertMovies(movieList.movies.map { it.toDatabase() })
            movieList
        } else {
            ListMovie(movies = repository.getAllMoviesFromDatabase().dropLast(20))
        }
    }
}