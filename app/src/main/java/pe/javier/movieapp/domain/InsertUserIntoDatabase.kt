package pe.javier.movieapp.domain

import pe.javier.movieapp.data.MovieAppRepository
import pe.javier.movieapp.data.database.entities.toDatabase
import pe.javier.movieapp.domain.model.User
import javax.inject.Inject

class InsertUserIntoDatabase @Inject constructor(
    private val repository: MovieAppRepository
) {
    suspend operator fun invoke(user: User) {
        repository.insertUser(user = user.toDatabase())
    }

}