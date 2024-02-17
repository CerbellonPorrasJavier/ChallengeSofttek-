package pe.javier.movieapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatesModel(
    @SerialName("maximum")
    val maximum: String,
    @SerialName("minimum")
    val minimum: String
)