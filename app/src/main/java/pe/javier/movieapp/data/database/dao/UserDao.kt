package pe.javier.movieapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pe.javier.movieapp.data.database.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    suspend fun getUser(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM user_table")
    suspend fun deleteUser()
}