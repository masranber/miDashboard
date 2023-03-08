package com.masranber.midashboard.lists.todo.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class TodoListItemObjectBoxDTO(
    @Id
    var id: Long,
    var name: String,
    var isComplete: Boolean,
    var createdTime: Long,
    var completedTime: Long?,
)
