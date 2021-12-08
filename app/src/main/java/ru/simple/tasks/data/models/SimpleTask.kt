package ru.simple.tasks.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.simple.tasks.util.Constants.DATABASE_TABLE

@Entity(tableName = DATABASE_TABLE)
data class SimpleTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)