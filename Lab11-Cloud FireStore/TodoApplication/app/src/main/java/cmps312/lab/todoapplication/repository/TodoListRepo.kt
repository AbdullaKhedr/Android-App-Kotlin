package cmps312.lab.todoapplication.repository

import cmps312.lab.todoapplication.model.Project
import cmps312.lab.todoapplication.model.Todo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object TodoListRepo {

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val projectDocumentRef by lazy { db.collection("projects") }

    suspend fun getProjects() = projectDocumentRef.get().await().toObjects(Project::class.java)
    fun addProject(project: Project) = projectDocumentRef.add(project)

}
