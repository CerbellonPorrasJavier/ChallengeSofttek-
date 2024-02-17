package pe.javier.movieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.javier.movieapp.data.database.dao.MovieDao
import pe.javier.movieapp.data.database.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao() : MovieDao

}