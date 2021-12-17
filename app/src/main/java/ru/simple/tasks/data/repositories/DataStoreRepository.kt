package ru.simple.tasks.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.simple.tasks.data.models.Priority
import ru.simple.tasks.util.Constants.PREFERENCE_KEY
import ru.simple.tasks.util.Constants.PREFERENCE_NAME
import java.io.IOException
import javax.inject.Inject

//храним приоритет сортировки

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(//Inject - аннотация, которая определяет вводимые конструкторы, методы и поля
    @ApplicationContext private val context: Context//ApplicationContext - аннотация для зависимости контекста приложения
) {
    private object PreferenceKeys {
        val sortKey = stringPreferencesKey(name = PREFERENCE_KEY)
    }

    private val dataStore = context.dataStore

    suspend fun persistSortState(priority: Priority) {
        dataStore.edit { preference ->
            //сохраняем значение во временное хранилище
            preference[PreferenceKeys.sortKey] = priority.name
        }
    }

    val readSortState: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            //получаем значение из временного хранилища
            val sortState = preferences[PreferenceKeys.sortKey]
                ?: Priority.NONE.name//если не удалось получить данные, то приоритет сортировки задач устанавливаем Priority.NONE
            sortState//Unit преобразуем в String
        }
}