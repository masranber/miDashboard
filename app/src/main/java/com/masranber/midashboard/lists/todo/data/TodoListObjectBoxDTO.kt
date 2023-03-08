package com.masranber.midashboard.lists.todo.data

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique
import io.objectbox.relation.ToMany

@Entity
data class TodoListObjectBoxDTO(
    @Id
    var id: Long = 0,
    @Unique
    var name: String = "",
) {
    lateinit var items: ToMany<TodoListItemObjectBoxDTO> // relations cannot be in primary constructor, best to make them lateinit to avoid nullability
}
