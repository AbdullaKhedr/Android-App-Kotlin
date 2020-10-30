package cmps312.lab.todoapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cmps312.lab.todoapplication.data.local.entity.Project
import cmps312.lab.todoapplication.data.local.entity.Todo

@Database(entities = [Project::class, Todo::class], version = 1, exportSchema = false)
abstract class TodoListDatabase : RoomDatabase() {
    abstract fun todoListDao() : TodoListDao

    // a single database instance
    companion object{
        @Volatile
        private var database : TodoListDatabase? = null
        private val DB_NAME = "todolist.db"

        @Synchronized
        fun getDatabse(context: Context):TodoListDatabase {
            if(database == null){
                database = Room.databaseBuilder(
                    context.applicationContext,
                    TodoListDatabase::class.java,
                    DB_NAME
                ).build()
            }
            return database as TodoListDatabase
        }
    }
}

