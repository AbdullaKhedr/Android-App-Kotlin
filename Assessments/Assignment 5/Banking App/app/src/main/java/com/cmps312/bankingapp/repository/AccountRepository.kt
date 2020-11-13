package com.cmps312.bankingapp.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.cmps312.bankingapp.entity.Account
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


object AccountRepository {

    private val firebaseDB by lazy { FirebaseFirestore.getInstance() }
    val projectsCollectionRef by lazy { firebaseDB.collection("Projects") }

    suspend fun getAccounts() = projectsCollectionRef.get().await().toObjects(Account::class.java)
    fun addAccount(account: Account) = projectsCollectionRef.add(account).addOnCompleteListener {
        if (it.isSuccessful) {
            Log.i(TAG, "addAccount: Successful")
        } else {
            Log.i(TAG, "addAccount: Fail")
        }
    }

    fun deleteAccount(account: Account) =
        projectsCollectionRef.document(account.accountNumber.toString()).delete()


}