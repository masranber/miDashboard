package com.masranber.midashboard.lists.todo.domain

data class TodoList(
    val id: Long,
    val name: String,
    val items: List<TodoListItem>,
) {
    constructor(name: String, item: List<TodoListItem>) : this(0, name, item)
}
