package cmps312.lab.todoapplication.repository

import android.util.Log
import cmps312.lab.todoapplication.model.Project
import cmps312.lab.todoapplication.model.Todo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import kotlinx.coroutines.tasks.await

object TodoListRepo {
    val TAG = "TodoListRepo"
    val db by lazy { FirebaseFirestore.getInstance() }
    val projectsDocumentRef by lazy { db.collection("projects") }
    val todosDocumentRef by lazy { db.collection("todos") }

    //all the magic of caching
    init {
        db.firestoreSettings = firestoreSettings { isPersistenceEnabled = true }
    }

    suspend fun getProjects() = projectsDocumentRef.get().await().toObjects(Project::class.java)
    fun addProject(project: Project) = projectsDocumentRef.add(project)
        .addOnSuccessListener { Log.d(TAG, "Successfully added new project") }
        .addOnFailureListener { Log.d(TAG, "Was not able to add the new project") }

    fun deleteProject(project: Project) = projectsDocumentRef.document(project.projectId).delete()

    suspend fun getTodoListByProject(pid: String): MutableList<Todo> {
        val querySnapshot = todosDocumentRef
            .whereEqualTo("projectId", pid)
            .get().await()

        val todos= mutableListOf<Todo>()
        querySnapshot.forEach {
            val todo = it.toObject(Todo::class.java)
            todo.todoId = it.id
            todos.add(todo)
        }
        return todos
    }

    suspend fun addTodo(todo: Todo) = todosDocumentRef.add(todo)
        .addOnSuccessListener { Log.d(TAG, "Successfully added new todo") }
        .addOnFailureListener { Log.d(TAG, "Was not able to add the new todo") }

    suspend fun getTodo(id: String) = todosDocumentRef.document(id).get().await().toObject(Todo::class.java)
    suspend fun deleteTodo(todo: Todo) = todo.todoId?.let { todosDocumentRef.document(it).delete() }
    suspend fun updateToDo(todo: Todo) = todo.todoId?.let { todosDocumentRef.document(it).set(todo) }
}
