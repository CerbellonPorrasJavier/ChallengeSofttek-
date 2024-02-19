package pe.javier.movieapp.data

import pe.javier.movieapp.data.database.dao.MovieDao
import pe.javier.movieapp.data.database.dao.UserDao
import pe.javier.movieapp.data.database.entities.MovieEntity
import pe.javier.movieapp.data.database.entities.UserEntity
import pe.javier.movieapp.data.network.MovieService
import pe.javier.movieapp.domain.model.ListMovie
import pe.javier.movieapp.domain.model.Movie
import pe.javier.movieapp.domain.model.User
import pe.javier.movieapp.domain.model.toDomain
import javax.inject.Inject

class MovieAppRepository @Inject constructor(
    private val api: MovieService,
    private val movieDao: MovieDao,
    private val userDao: UserDao
) {

    suspend fun getAllMoviesFromApi(page: Int = 1): ListMovie? {
        val response = api.getMovies(page = page)
        return response?.toDomain()
    }

    suspend fun getValidUserFromApi(): User? {
        val response = api.getValidUser()
        return response?.toDomain()
    }

    suspend fun getAllMoviesFromDatabase(): List<Movie> {
        val response = movieDao.getAllMovies()
        return response?.map { it.toDomain() } ?: emptyList()
    }

    suspend fun getValidUserFromDatabase(): User? {
        val response = userDao.getUser()
        return response?.toDomain()
    }

    suspend fun insertMovies(movies: List<MovieEntity>) {
        movieDao.insertAllMovies(movies)
    }

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun clearUser() {
        userDao.deleteUser()
    }

    suspend fun clearMovies() {
        movieDao.deleteAllMovies()
    }

}