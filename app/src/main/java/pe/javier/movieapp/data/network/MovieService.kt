package pe.javier.movieapp.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import pe.javier.movieapp.data.model.ListMovieModel
import pe.javier.movieapp.data.model.UserModel
import javax.inject.Inject

class MovieService @Inject constructor(
    private val api: MovieApiClient
) {
    suspend fun getMovies(page: Int, apiKey: String): ListMovieModel? {
        return withContext(Dispatchers.IO) {
            api.getAllMovies(page = page, apiKey = apiKey).body()
        }
    }

    suspend fun getValidUsers(): List<UserModel> {
        return withContext(Dispatchers.IO) {
            listOf(UserModel("Admin", "Password*123"))
        }
    }

}