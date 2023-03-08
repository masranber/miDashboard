package com.masranber.midashboard.lists

import com.masranber.midashboard.lists.todo.domain.TodoList

data class TodoListPrefs(
    val id: Long,
    val selectedList: TodoList
)
