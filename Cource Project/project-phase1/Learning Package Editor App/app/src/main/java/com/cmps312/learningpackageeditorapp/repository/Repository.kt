package com.cmps312.learningpackageeditorapp.repository

import android.content.Context
import com.cmps312.learningpackageeditorapp.model.LearningPackage
import com.cmps312.learningpackageeditorapp.model.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object Repository {
    var learningPackage = mutableListOf<LearningPackage>()
    var users = mutableListOf<User>()

    private fun readData(filename: String, context: Context) = context.assets
        .open(filename)
        .bufferedReader().use { it.readText() }

    fun getLearningPackages(context: Context): MutableList<LearningPackage> {
        learningPackage =
            Json { ignoreUnknownKeys = true }.decodeFromString(readData("packages.json", context))
        return learningPackage
    }

    fun getUsers(context: Context): MutableList<User> {
        users =
            Json { ignoreUnknownKeys = true }.decodeFromString(readData("users.json", context))
        return users
    }

    // TODO: Should we implement it that way?
//    val learningPackages by lazy {
//        val data = context.assets.open("packages.json")
//            .bufferedReader().use { it.readText() }
//        Json.decodeFromString<List<LearningPackage>>(data)
//    }

}