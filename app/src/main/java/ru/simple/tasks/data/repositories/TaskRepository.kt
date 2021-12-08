package ru.simple.tasks.data.repositories

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import ru.simple.tasks.data.TaskDao
import ru.simple.tasks.data.models.SimpleTask
import javax.inject.Inject

@ViewModelScoped//означает, что экземпляр TaskRepository существует в течении всего срока службы ViewModel
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {
    //предоставляем внешнюю зависимость программному компоненту
    val getAllTasks: Flow<List<SimpleTask>> = taskDao.getAllTasks()
    val sortByLowPriority: Flow<List<SimpleTask>> = taskDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<SimpleTask>> = taskDao.sortByHighPriority()

    suspend fun addTask(simpleTask: SimpleTask){
        taskDao.addTask(simpleTask = simpleTask)
    }

    suspend fun updateTask(simpleTask: SimpleTask){
        taskDao.updateTask(simpleTask = simpleTask)
    }

    suspend fun deleteTask(simpleTask: SimpleTask){
        taskDao.deleteTask(simpleTask = simpleTask)
    }

    suspend fun deleteAllTasks(){
        taskDao.deleteAllTasks()
    }

    fun getSelectedTask(taskId: Int): Flow<SimpleTask> = taskDao.getSelectedTask(taskId = taskId)

    fun searchDatabase(searchQuery: String): Flow<List<SimpleTask>> = taskDao.searchDatabase(searchQuery = searchQuery)
}