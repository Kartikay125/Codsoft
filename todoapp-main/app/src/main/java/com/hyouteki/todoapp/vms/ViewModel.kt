package com.hyouteki.todoapp.vms

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hyouteki.todoapp.dbs.TodoDb
import com.hyouteki.todoapp.models.Todo
import com.hyouteki.todoapp.repos.TodoRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val todoRepo: TodoRepo = TodoRepo(TodoDb.getDatabase(application).getTodoDao())
    val allTodos: LiveData<List<Todo>> = todoRepo.all
    fun insertTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepo.insert(todo)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepo.delete(todo)
    }

    fun updateTodo(id: Int, title: String) = viewModelScope.launch(Dispatchers.IO) {
        todoRepo.update(id, title)
    }

    fun toggleDoneTodo(id: Int, done: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        todoRepo.toggleDone(id, done)
    }

    fun clearTodo() = viewModelScope.launch(Dispatchers.IO) {
        todoRepo.clear()
    }
}