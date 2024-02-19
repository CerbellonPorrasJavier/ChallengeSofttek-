package pe.javier.movieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.javier.movieapp.data.database.dao.MovieDao
import pe.javier.movieapp.data.database.dao.UserDao
import pe.javier.movieapp.data.database.entities.MovieEntity
import pe.javier.movieapp.data.database.entities.UserEntity

@Database(entities = [MovieEntity::class, UserEntity::class], version = 1)
abstract class MovieAppDatabase : RoomDatabase() {

    abstract fun getMovieDao() : MovieDao

    abstract fun getUserDao() : UserDao

}