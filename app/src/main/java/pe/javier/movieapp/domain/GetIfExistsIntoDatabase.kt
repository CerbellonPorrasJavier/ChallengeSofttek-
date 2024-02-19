package pe.javier.movieapp.domain

import pe.javier.movieapp.data.MovieAppRepository
import javax.inject.Inject

class GetIfExistsIntoDatabase @Inject constructor(
    private val repository: MovieAppRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.getValidUserFromDatabase() != null
    }

}