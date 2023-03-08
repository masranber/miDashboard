package com.masranber.midashboard.lists.todo.domain

import com.masranber.midashboard.domain.Resource
import com.masranber.midashboard.lists.TodoListPrefs
import com.masranber.midashboard.lists.TodoListPrefsObjectBoxDTO
import com.masranber.midashboard.lists.todo.data.TodoListItemObjectBoxDTO
import com.masranber.midashboard.lists.todo.data.TodoListObjectBoxDTO
import com.masranber.midashboard.lists.todo.data.TodoListObjectBoxDTO_
import com.masranber.midashboard.util.objectbox.ObjectBox
import io.objectbox.Box
import io.objectbox.exception.UniqueViolationException
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.flow
import io.objectbox.query.QueryBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import java.time.ZonedDateTime

enum class TodoListError {
    ERROR_LIST_NOT_FOUND,
    ERROR_LIST_DUPLICATE_NAME,
    ERROR_LIST_NOT_SELECTED,
}

object TodoListRepository {

    private val todoListBox: Box<TodoListObjectBoxDTO> by lazy { ObjectBox.get().boxFor() }
    private val todoListItemBox: Box<TodoListItemObjectBoxDTO> by lazy { ObjectBox.get().boxFor() }
    private val todoListPrefsBox: Box<TodoListPrefsObjectBoxDTO> by lazy { ObjectBox.get().boxFor() }

    fun getAllTodoLists() : Resource<List<TodoList>, TodoListError> {
        return Resource.Success(todoListBox.all.map { it.toTodoList() })
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTodoListPrefs() : Resource<Flow<TodoListPrefs>, TodoListError> {
        return Resource.Success(todoListPrefsBox.query().build().flow().map { it.firstOrNull() }.mapNotNull { it?.toTodoListPrefs() })
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTodoListWithName(name: String) : Resource<Flow<TodoList>, TodoListError> {
        val list = todoListBox.query()
            .equal(
                TodoListObjectBoxDTO_.name, name,
                QueryBuilder.StringOrder.CASE_SENSITIVE
            )
            .build()
        return when(list.count() > 0) {
            true -> Resource.Success(list.flow().map { it.firstOrNull() }.mapNotNull { it?.toTodoList() })
            false -> Resource.Error(TodoListError.ERROR_LIST_NOT_FOUND)
        }
    }

    fun putTodoList(todoList: TodoList) : TodoListError? {
        return try {
            val entity = todoList.toTodoListObjectBoxDTO()
            todoListBox.attach(entity)
            todoListBox.put(entity)
            null
        } catch(e: UniqueViolationException) {
            TodoListError.ERROR_LIST_DUPLICATE_NAME
        }
    }

    fun putTodoListItem(todoListItem: TodoListItem) : TodoListError? {
        todoListItemBox.put(todoListItem.toTodoListItemObjectBoxDTO())
        return null
    }

    fun deleteTodoList(todoList: TodoList) : TodoListError? {
        return if(todoListBox.remove(todoList.id)) null else TodoListError.ERROR_LIST_NOT_FOUND // skip mapping DTO cause we just need the ID (avoids copying list)
    }

    fun isEmpty() : Resource<Boolean, TodoListError> {
        return Resource.Success(todoListBox.count() == 0L)
    }

    fun reset() {
        todoListBox.removeAll()
        todoListItemBox.removeAll()
        todoListPrefsBox.removeAll()
    }

}