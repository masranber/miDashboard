package com.masranber.midashboard.lists.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masranber.midashboard.lists.todo.domain.TodoList
import com.masranber.midashboard.lists.todo.domain.TodoListItem
import com.masranber.midashboard.lists.todo.domain.TodoListRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class TodoListViewModel : ViewModel() {

    private val _todoList = MutableStateFlow(TodoList("", emptyList()))
    val todoList: StateFlow<TodoList> = _todoList

    init {
        viewModelScope.launch {
            //TodoListRepository.reset()
            if(TodoListRepository.isEmpty().data == true) {
                val items = mutableListOf<TodoListItem>()
                repeat(15) {
                    items.add(TodoListItem("Item ${it+1}", false, ZonedDateTime.now(), null))
                }
                val groceriesList = TodoList("Groceries", items)
                Log.i("TodoList", groceriesList.toString())
                TodoListRepository.putTodoList(groceriesList)
                //val defaultTodoList = TodoList("Todo", emptyList())
                //TodoListRepository.putTodoList(defaultTodoList)
            }
        }
        viewModelScope.launch {
            val response = TodoListRepository.getTodoListWithName("Groceries")
            response.data?.let {
                _todoList.value = it.first()
            }
            //combine()
        }
    }

    /*fun addTodoListItem(name: String) {
        val todoList = _todoList.value
        todoList.items.add(TodoListItem(
            name = name,
            isComplete = false,
            created = ZonedDateTime.now(),
            completed = null
        ))
        TodoListRepository.putTodoList(todoList)
    }*/

    fun addTodoListItem(name: String) {
        val item = TodoListItem(
            name = name,
            isComplete = false,
            created = ZonedDateTime.now(),
            completed = null
        )
        val todoList = _todoList.value.copy(items = _todoList.value.items + listOf(item))
        TodoListRepository.putTodoList(todoList)
    }

    fun setTodoListItemName(itemIndex: Int, name: String) {
        val updated = todoList.value.items[itemIndex].copy(name = name)
        TodoListRepository.putTodoListItem(updated)
        _todoList.value = _todoList.value.copy(items = _todoList.value.items.toMutableList().also { it[itemIndex] = updated })
        //TodoListRepository.putTodoList(todoList.value)
    }

    fun setTodoListItemComplete(itemIndex: Int, isComplete: Boolean) {
        val updated = todoList.value.items[itemIndex].copy(isComplete = isComplete)
        TodoListRepository.putTodoListItem(updated)
        _todoList.value = _todoList.value.copy(items = _todoList.value.items.toMutableList().also { it[itemIndex] = updated })
        //TodoListRepository.putTodoList(todoList.value)
    }

    fun updateTodoListItem(todoListItem: TodoListItem) {
        TodoListRepository.putTodoListItem(todoListItem)
    }

    fun updateTodoListItemName(name: String) {

    }

    /*fun setTodoListName(name: String) {
        val todoList = _todoList.value
        todoList.name = name
        val error = TodoListRepository.putTodoList(todoList)
        error?.let {

        }
    }*/
}