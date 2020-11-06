package cmps312.lab.todoapplication.ui.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cmps312.lab.todoapplication.model.Project
import cmps312.lab.todoapplication.model.Todo
import cmps312.lab.todoapplication.repository.TodoListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProjectViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "ProjectViewModel"
    private var _projects = MutableLiveData<List<Project>>()
    private var _todos = MutableLiveData<List<Todo>>()

    var projects: LiveData<List<Project>> = _projects
    var todos: LiveData<List<Todo>> = _todos

    lateinit var selectedTodo: Todo
    var selectedProject: Project? = null

    init {
        registerProjectListener()
        registerTodoListener()
    }

    fun getTodos(projectId: String) {
        _todos.value = listOf<Todo>() //clear the list
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                _todos.value = TodoListRepo.getTodoListByProject(projectId)
            }
        }
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoListRepo.addTodo(todo).await()
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoListRepo.deleteTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoListRepo.updateToDo(todo)
        }
    }

    fun addProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoListRepo.addProject(project)
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoListRepo.deleteProject(project)
        }
    }

    private fun registerProjectListener(){
        TodoListRepo.projectsDocumentRef.addSnapshotListener { snapshot, error ->
           if(error != null) return@addSnapshotListener

            val projects = mutableListOf<Project>()
            snapshot?.forEach {
                val project = it.toObject(Project::class.java)
                project.projectId = it.id
                projects.add(project)
            }
            _projects.value = projects
        }

    }
    private fun registerTodoListener(){
        TodoListRepo.todosDocumentRef.addSnapshotListener { snapshot, error ->
            if(error != null) return@addSnapshotListener

            val todos = mutableListOf<Todo>()
            snapshot?.forEach {
                val todo = it.toObject(Todo::class.java)
                todo.todoId = it.id
                if(todo.projectId == selectedProject?.projectId)
                todos.add(todo)
            }
            _todos.value = todos
        }

    }
}





