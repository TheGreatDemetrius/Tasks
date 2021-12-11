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
import ru.simple.tasks.data.models.SimpleTask
import ru.simple.tasks.data.repositories.TaskRepository
import ru.simple.tasks.util.SearchAppBarState
import javax.inject.Inject

@HiltViewModel//необходимо для внедрения ViewModel
class SharedViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)//указываем, что по умолчанию поисковая строка будет закрыта

    val searchTexState: MutableState<String> = mutableStateOf("")//указываем, что по умолчанию поисковая строка будет пуста

    private val _allTasks =
        MutableStateFlow<List<SimpleTask>>(emptyList())
    val allTasks: StateFlow<List<SimpleTask>> = _allTasks

    fun getAllTasks() {
        viewModelScope.launch { //coroutine привязанная к жизненному циклу ViewModel
            repository.getAllTasks.collect {//получаем все задачи из базы данных
                _allTasks.value = it
            }
        }
    }
}