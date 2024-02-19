package pe.javier.movieapp.domain

import pe.javier.movieapp.data.MovieAppRepository
import javax.inject.Inject

class ClearDataUseCase @Inject constructor(
    private val repository: MovieAppRepository
) {

    suspend operator fun invoke() {
        repository.clearUser()
        repository.clearMovies()
    }

}