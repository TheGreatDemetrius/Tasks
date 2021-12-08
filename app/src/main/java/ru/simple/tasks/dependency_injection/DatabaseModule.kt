package ru.simple.tasks.dependency_injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.simple.tasks.data.models.TaskDatabase
import ru.simple.tasks.util.Constants.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton//означает, что в приложении будет использоваться только один экземпляр конструктора базы данных
    @Provides//аннотирует методы модуля для создания привязки метода поставщика
    fun provideDatabase(
        @ApplicationContext//аннотация для зависимости контекста приложения
        context: Context
    ) = Room.databaseBuilder(
        context,
        TaskDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: TaskDatabase) = database.taskDao()//возвращаем значение TaskDao
}