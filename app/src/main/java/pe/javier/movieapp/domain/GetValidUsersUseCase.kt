package pe.javier.movieapp.domain

import pe.javier.movieapp.data.MovieRepository
import pe.javier.movieapp.domain.model.User
import javax.inject.Inject

class GetValidUsersUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<User> {
        return repository.getValidUsersFromApi()
    }
}