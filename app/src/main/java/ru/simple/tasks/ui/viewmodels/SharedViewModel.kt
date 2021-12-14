package ru.simple.tasks.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.simple.tasks.data.models.Priority
import ru.simple.tasks.data.models.SimpleTask
import ru.simple.tasks.data.repositories.TaskRepository
import ru.simple.tasks.util.Constants.MAX_TITLE_LENGTH
import ru.simple.tasks.util.RequestState
import ru.simple.tasks.util.SearchAppBarState
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel//необходимо для внедрения ViewModel
class SharedViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)//указываем, что по умолчанию поисковая строка будет закрыта

    val searchTexState: MutableState<String> =
        mutableStateOf("")//указываем, что по умолчанию поисковая строка будет пуста

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