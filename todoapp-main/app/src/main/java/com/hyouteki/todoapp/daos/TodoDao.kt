package com.hyouteki.todoapp.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hyouteki.todoapp.models.Todo

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: Todo)

    @Delete
    fun delete(todo: Todo)

    @Query("Update todo_table set title = :title where id = :id")
    fun update(id: Int, title: String)

    @Query("update todo_table set done = :done where id = :id")
    fun toggleDone(id: Int, done: Boolean)

    @Query("Select * from todo_table order by id DESC")
    fun all(): LiveData<List<Todo>>

    @Query("Delete from todo_table")
    fun clear()
}