package com.masranber.midashboard.lists

import com.masranber.midashboard.lists.todo.data.TodoListObjectBoxDTO
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class TodoListPrefsObjectBoxDTO(
    @Id
    var id: Long = 0,
) {
    lateinit var selectedList: ToOne<TodoListObjectBoxDTO>
}
