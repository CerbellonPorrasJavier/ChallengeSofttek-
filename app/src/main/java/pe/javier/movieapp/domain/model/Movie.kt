package pe.javier.movieapp.domain.model

import pe.javier.movieapp.data.database.entities.MovieEntity
import pe.javier.movieapp.data.model.MovieModel

data class Movie(
    val id: Long,
    val title: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double
)

fun MovieModel.toDomain() = Movie(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)

fun MovieEntity.toDomain() = Movie(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)
