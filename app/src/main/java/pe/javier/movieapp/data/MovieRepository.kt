package pe.javier.movieapp.data

import pe.javier.movieapp.data.database.dao.MovieDao
import pe.javier.movieapp.data.database.entities.MovieEntity
import pe.javier.movieapp.data.network.MovieService
import pe.javier.movieapp.domain.model.ListMovie
import pe.javier.movieapp.domain.model.Movie
import pe.javier.movieapp.domain.model.User
import pe.javier.movieapp.domain.model.toDomain
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieService,
    private val movieDao: MovieDao
) {

    suspend fun getAllMoviesFromApi(page: Int = 1, apiKey: String): ListMovie? {
        val response = api.getMovies(page = page, apiKey = apiKey)
        return response?.toDomain()
    }

    suspend fun getValidUsersFromApi(): List<User> {
        val response = api.getValidUsers()
        return response.map { it.toDomain() }
    }

    suspend fun getAllMoviesFromDatabase(): List<Movie> {
        val response = movieDao.getAllMovies()
        return response?.map { it.toDomain() } ?: emptyList()
    }

    suspend fun insertMovies(movies: List<MovieEntity>) {
        movieDao.insertAllMovies(movies)
    }

    suspend fun clearMovies() {
        movieDao.deleteAllMovies()
    }

}