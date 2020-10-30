package cmps312.lab.todoapplication.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import cmps312.lab.todoapplication.data.local.TodoListDatabase
import cmps312.lab.todoapplication.data.local.entity.Project
import cmps312.lab.todoapplication.data.local.entity.Todo

class TodoListRepo(private val context: Context) {

    private val todoListDao by lazy {
        TodoListDatabase.getDatabse(context).todoListDao()
    }
    fun getProjects() = todoListDao.getProjects()
    suspend fun addProject(project: Project)= todoListDao.addProject(project)
    suspend fun deleteProject(project: Project)= todoListDao.deleteProject(project)
    suspend fun updateProject(project: Project) = todoListDao.updateProject(project)
    fun getTodosByProject(pid: Int)= todoListDao.getTodosByProject(pid)
    fun getTodo(id: Int) = todoListDao.getTodo(id)
    suspend fun addTodo(todo: Todo) = todoListDao.addTodo(todo)
    suspend fun updateTodo(todo: Todo)  = todoListDao.updateTodo(todo)
    suspend fun deleteTodo(todo: Todo)  = todoListDao.deleteTodo(todo)
}