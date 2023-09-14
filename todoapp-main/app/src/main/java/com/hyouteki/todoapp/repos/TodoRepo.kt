package com.hyouteki.todoapp.repos

import com.hyouteki.todoapp.daos.TodoDao
import com.hyouteki.todoapp.models.Todo

class TodoRepo(private val dao: TodoDao) {
    val all = dao.all()
    suspend fun insert(todo: Todo) = dao.insert(todo)
    suspend fun delete(todo: Todo) = dao.delete(todo)
    suspend fun update(id: Int, title: String) = dao.update(id, title)
    suspend fun toggleDone(id: Int, done: Boolean) = dao.toggleDone(id, done)
    suspend fun clear() = dao.clear()
}