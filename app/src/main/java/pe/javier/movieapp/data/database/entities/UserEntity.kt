package pe.javier.movieapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pe.javier.movieapp.domain.model.User

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "user")
    val user: String,
    @ColumnInfo(name = "password")
    val password: String
)

fun User.toDatabase() = UserEntity(id = id, user = user, password = password)