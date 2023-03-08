package com.masranber.midashboard.lists.todo.domain

import com.masranber.midashboard.lists.TodoListPrefs
import com.masranber.midashboard.lists.TodoListPrefsObjectBoxDTO
import com.masranber.midashboard.lists.todo.data.TodoListItemObjectBoxDTO
import com.masranber.midashboard.lists.todo.data.TodoListObjectBoxDTO
import com.masranber.midashboard.util.objectbox.ObjectBox
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.relation.ToMany
import java.time.Instant
import java.time.ZoneId

fun TodoListItemObjectBoxDTO.toTodoListItem() = TodoListItem(
    id = this.id,
    name = this.name,
    isComplete = this.isComplete,
    created = Instant.ofEpochSecond(this.createdTime).atZone(ZoneId.systemDefault()),
    completed = this.completedTime?.let { Instant.ofEpochSecond(it).atZone(ZoneId.systemDefault()) },
)

fun TodoListItem.toTodoListItemObjectBoxDTO() = TodoListItemObjectBoxDTO(
    id = this.id,
    name = this.name,
    isComplete = this.isComplete,
    createdTime = this.created.toEpochSecond(),
    completedTime = this.completed?.toEpochSecond(),
)

fun TodoListObjectBoxDTO.toTodoList() = TodoList(
    id = this.id,
    name = this.name,
    items = this.items.map { it.toTodoListItem() }.toMutableList()
)

fun TodoList.toTodoListObjectBoxDTO() : TodoListObjectBoxDTO {
    val dto = TodoListObjectBoxDTO(this.id, this.name)
    val box: Box<TodoListObjectBoxDTO> = ObjectBox.get().boxFor()
    box.attach(dto)
    this.items.forEach {
        dto.items.add(it.toTodoListItemObjectBoxDTO())
    }
    return dto
}

fun TodoListPrefsObjectBoxDTO.toTodoListPrefs() = TodoListPrefs(
    id = this.id,
    selectedList = this.selectedList.target.toTodoList()
)

fun TodoListPrefs.toTodoListPrefsObjectBoxDTO() = TodoListPrefsObjectBoxDTO(
    id = this.id
).also { it.selectedList.target = this.selectedList.toTodoListObjectBoxDTO() }