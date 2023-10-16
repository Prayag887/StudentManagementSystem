package com.example.studentmanagementsystem.StudentData


//THIS IS DTO

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class User(
    val name: String,
    val phone: String,
    val email: String,
    val grade: String,
    val age: String,
    val book_library: String
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var roll_no: Int = 0

    override fun toString(): String {
        return name
    }
}

