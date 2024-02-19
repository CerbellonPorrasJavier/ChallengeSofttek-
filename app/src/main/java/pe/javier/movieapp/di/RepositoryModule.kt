package pe.javier.movieapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pe.javier.movieapp.data.MovieAppRepository
import pe.javier.movieapp.data.database.dao.MovieDao
import pe.javier.movieapp.data.database.dao.UserDao
import pe.javier.movieapp.data.network.MovieService

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMovieRepository(
        movieService: MovieService,
        movieDao: MovieDao,
        userDao: UserDao
    ): MovieAppRepository {
        return MovieAppRepository(movieService, movieDao, userDao)
    }
}