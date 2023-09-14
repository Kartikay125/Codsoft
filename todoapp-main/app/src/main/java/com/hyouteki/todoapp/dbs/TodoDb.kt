package com.hyouteki.todoapp.dbs

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hyouteki.todoapp.daos.TodoDao
import com.hyouteki.todoapp.models.Todo

@Database(
    entities = [Todo::class], version = 1, exportSchema = false
)
abstract class TodoDb : RoomDatabase() {
    abstract fun getTodoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: TodoDb? = null
        fun getDatabase(context: Context): TodoDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDb::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}