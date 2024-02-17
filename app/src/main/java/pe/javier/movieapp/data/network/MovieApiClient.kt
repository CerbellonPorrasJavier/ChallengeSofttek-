package pe.javier.movieapp.data.network

import pe.javier.movieapp.data.model.ListMovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApiClient {

    @GET("upcoming")
    suspend fun getAllMovies( @Header("api_key") apiKey: String, @Query("page") page: Int) : Response<ListMovieModel>


}