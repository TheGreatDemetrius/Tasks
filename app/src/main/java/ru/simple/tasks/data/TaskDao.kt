package ru.simple.tasks.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.simple.tasks.data.models.SimpleTask
import ru.simple.tasks.util.Constants.DATABASE_TABLE

@Dao
interface TaskDao {
    @Query("SELECT * FROM $DATABASE_TABLE ORDER BY id ASC")
    fun getAllTasks(): Flow<List<SimpleTask>>//запрашиваем все задачи в асинхронном потоке

    @Query("SELECT * FROM $DATABASE_TABLE WHERE id = :taskId")//taskId получаем из параметров getSelectedTask
    fun getSelectedTask(taskId: Int): Flow<SimpleTask>//запрашиваем одну задачу в асинхронном потоке

    @Insert(onConflict = OnConflictStrategy.IGNORE)//вставляем задачу simpleTask в базу данных
    suspend fun addTask(simpleTask: SimpleTask)//с помощью suspend приостанавливаем поток, т.к. эту функцию НЕ запускаем в асинхронном потоке

    @Update//обновляем задачу simpleTask в базе данных
    suspend fun updateTask(simpleTask: SimpleTask)

    @Delete//удаляем задачу simpleTask из базы данных
    suspend fun deleteTask(simpleTask: SimpleTask)

    @Query("DELETE FROM $DATABASE_TABLE")//удаляем все задачи из базы данных
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM $DATABASE_TABLE WHERE title LIKE :searchQuery OR description LIKE :searchQuery")//ищем задачу в базе данных по заголовку или описанию
    fun searchDatabase(searchQuery: String): Flow<List<SimpleTask>>//запрашиваем список задач в асинхронном потоке

    @Query("SELECT * FROM $DATABASE_TABLE ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    //сортируем задачи по низкому приоритету (1 - низкий, 2 - средний, 3 - высокий приоритет)
    fun sortByLowPriority(): Flow<List<SimpleTask>>//запрашиваем список задач в асинхронном потоке

    @Query("SELECT * FROM $DATABASE_TABLE ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    //сортируем задачи по низкому приоритету (1 - высокий, 2 - средний, 3 - низкий приоритет)
    fun sortByHighPriority(): Flow<List<SimpleTask>>//запрашиваем список задач в асинхронном потоке
}