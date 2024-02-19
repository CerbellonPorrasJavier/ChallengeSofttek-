package pe.javier.movieapp.domain

import pe.javier.movieapp.data.MovieAppRepository
import pe.javier.movieapp.domain.model.User
import javax.inject.Inject

class GetValidUserUseCase @Inject constructor(
    private val repository: MovieAppRepository
) {
    suspend operator fun invoke(): User? {
        val user = repository.getValidUserFromApi()
        return user ?: repository.getValidUserFromDatabase()
    }
}