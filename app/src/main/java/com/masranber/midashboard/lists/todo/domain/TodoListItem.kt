package com.masranber.midashboard.lists.todo.domain

import java.time.ZonedDateTime

data class TodoListItem(
    val id: Long,
    val name: String,
    val isComplete: Boolean,
    val created: ZonedDateTime,
    val completed: ZonedDateTime?,
) {
    constructor(name: String, isComplete: Boolean, created: ZonedDateTime, completed: ZonedDateTime?) : this(0, name, isComplete, created, completed)
}
