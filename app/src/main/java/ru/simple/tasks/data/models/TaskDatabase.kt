package ru.simple.tasks.data.models

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.simple.tasks.data.TaskDao

@Database(entities = [SimpleTask::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}