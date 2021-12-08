package ru.simple.tasks.data.repositories

import kotlinx.coroutines.flow.Flow
import ru.simple.tasks.data.TaskDao
import ru.simple.tasks.data.models.SimpleTask
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
    //предоставляем внешнюю зависимость программному компоненту
    val getAllTasks: Flow<List<SimpleTask>> = taskDao.getAllTasks()
    val sortByLowPriority: Flow<List<SimpleTask>> = taskDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<SimpleTask>> = taskDao.sortByHighPriority()

    fun getSelectedTask(taskId: Int): Flow<SimpleTask> = taskDao.getSelectedTask(taskId = taskId)

}