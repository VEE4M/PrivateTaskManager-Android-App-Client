package com.gmail.appverstas.privatetaskmanagerapp.di

import android.content.Context
import androidx.room.Room
import com.gmail.appverstas.privatetaskmanagerapp.data.TaskDatabase
import com.gmail.appverstas.privatetaskmanagerapp.other.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *Veli-Matti Tikkanen, 26.7.2021
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTaskDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, TaskDatabase::class.java , DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideTaskDao(db: TaskDatabase) = db.taskDao()



}