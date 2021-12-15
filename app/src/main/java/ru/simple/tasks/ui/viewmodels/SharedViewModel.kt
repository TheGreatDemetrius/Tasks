package ru.simple.tasks.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.simple.tasks.data.models.Priority
import ru.simple.tasks.data.models.SimpleTask
import ru.simple.tasks.data.repositories.DataStoreRepository
import ru.simple.tasks.data.repositories.TaskRepository
import ru.simple.tasks.util.Action
import ru.simple.tasks.util.Constants.MAX_TITLE_LENGTH
import ru.simple.tasks.util.RequestState
import ru.simple.tasks.util.SearchAppBarState
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel//необходимо для внедрения ViewModel
class SharedViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)//указываем, что по умолчанию поисковая строка будет закрыта

    val searchTexState: MutableState<String> =
        mutableStateOf("")//указываем, что по умолчанию поисковая строка будет пуста

    private val _searchedTasks =
        MutableStateFlow<RequestState<List<SimpleTask>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<SimpleTask>>> = _searchedTasks

    fun searchDatabase(searchQuery: String) {
        _searchedTasks.value = RequestState.Loading//переходим в состояние загрузки данных
        try {
            viewModelScope.launch {
                repository.searchDatabase(searchQuery = "%$searchQuery%")
                    .collect { searchedTasks ->
                        _searchedTasks.value = RequestState.Success(searchedTasks)
                    }
            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(e)//переходим в состояние ошибки
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED//обновляем состояние поисковой строки
    }

    private val _allTasks =
        MutableStateFlow<RequestState<List<SimpleTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<SimpleTask>>> = _allTasks

    fun getAllTasks() {
        _allTasks.value = RequestState.Loading//переходим в состояние загрузки данных
        try {
            viewModelScope.launch { //coroutine привязанная к жизненному циклу ViewModel
                repository.getAllTasks.collect {//получаем все задачи из базы данных
                    _allTasks.value =
                        RequestState.Success(it)//переходим в состояние успешной загрузки данных
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)//переходим в состояние ошибки
        }
    }

    private val _selectedTask: MutableStateFlow<SimpleTask?> = MutableStateFlow(value = null)
    val selectedTask: StateFlow<SimpleTask?> = _selectedTask

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val simpleTask = SimpleTask(
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.addTask(simpleTask = simpleTask)
        }
        resetSearchedTask()
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val simpleTask = SimpleTask(
                id = id.value,//обновляем по идентификатору
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.updateTask(simpleTask = simpleTask)
        }
        resetSearchedTask()
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val simpleTask = SimpleTask(
                id = id.value,//удаляем по идентификатору
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.deleteTask(simpleTask = simpleTask)
        }
        resetSearchedTask()
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }


    private fun resetSearchedTask() {
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    fun handleDatabaseActions(action: Action) {//выполняем полученное действие
        when (action) {
            Action.ADD -> addTask()
            Action.UPDATE -> updateTask()
            Action.DELETE -> deleteTask()
            Action.DELETE_ALL -> deleteAllTasks()
            Action.UNDO -> addTask()
            else -> {}//если нажали кнопку назад (т.е. NO_ACTION)
        }
        this.action.value = Action.NO_ACTION//устанавливаем значение по умолчанию
    }

    fun updateTaskFields(selectedTask: SimpleTask?) {
        if (selectedTask != null) {//если нажали на конкретную задачу
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {//если добавляем новую задачу
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    fun updateTitle(upTitle: String) {
        if (upTitle.length < MAX_TITLE_LENGTH)//проверяем количество символов в заголовоке
            title.value = upTitle
    }
}