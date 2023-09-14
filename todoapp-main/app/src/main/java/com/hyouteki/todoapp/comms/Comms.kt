package com.hyouteki.todoapp.comms

import com.hyouteki.todoapp.models.Todo

interface Comms {
    fun insertTodoAPI(todo: Todo) {}
    fun makeToast(message: String) {}
}