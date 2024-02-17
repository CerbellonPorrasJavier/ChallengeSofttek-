package pe.javier.movieapp.domain.model

import pe.javier.movieapp.data.model.UserModel

data class User(
    val user: String,
    val password: String
)

fun UserModel.toDomain() = User(user = user, password = password)