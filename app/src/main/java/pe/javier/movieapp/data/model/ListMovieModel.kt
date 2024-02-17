package pe.javier.movieapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListMovieModel(
    @SerialName("dates")
    val dates: DatesModel,
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val movies: List<MovieModel>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)