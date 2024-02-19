package pe.javier.movieapp.domain.model

import pe.javier.movieapp.data.database.entities.UserEntity
import pe.javier.movieapp.data.model.UserModel

data class User(
    val id: Int = 1,
    val user: String,
    val password: String
)

fun UserModel.toDomain() = User(id= id, user = user, password = password)
fun UserEntity.toDomain() = User(id= id, user = user, password = password)