package com.example.studentmanagementsystem.StudentData

import androidx.room.*
import com.example.studentmanagementsystem.StudentData.User

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user")
    suspend fun getAllUser(): List<User>

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT name FROM user")
    suspend fun getNames(): List<String>
}