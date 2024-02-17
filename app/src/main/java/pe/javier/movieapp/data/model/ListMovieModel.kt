package pe.javier.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class ListMovieModel(
    @SerializedName("dates")
    val dates: DatesModel,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movies: List<MovieModel>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)