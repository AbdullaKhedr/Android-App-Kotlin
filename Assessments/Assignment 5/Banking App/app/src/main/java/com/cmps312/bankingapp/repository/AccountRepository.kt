package com.cmps312.bankingapp.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.cmps312.bankingapp.entity.Account
import com.cmps312.bankingapp.entity.Transaction
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestoreSettings
import kotlinx.coroutines.tasks.await


object AccountRepository {

    private val firebaseDB by lazy { FirebaseFirestore.getInstance() }
    val projectsCollectionRef by lazy { firebaseDB.collection("Projects") }
    val transactionsCollectionRef by lazy { firebaseDB.collection("Transactions") }

    init {
        // Cashing
        firebaseDB.firestoreSettings = firestoreSettings { isPersistenceEnabled = true }
    }

    suspend fun getAccounts() = projectsCollectionRef.get().await().toObjects(Account::class.java)
    fun addAccount(account: Account) = projectsCollectionRef.add(account).addOnCompleteListener {
        if (it.isSuccessful) {
            Log.i(TAG, "addAccount: Successful")
        } else {
            Log.i(TAG, "addAccount: Fail")
        }
    }

    fun deleteAccount(account: Account) {
        projectsCollectionRef.document(account.accountNumber).delete()
        deleteTransForAcct(account.accountNumber)
    }

    private fun deleteTransForAcct(acctNo: String) {
        transactionsCollectionRef.whereEqualTo("accountNo", acctNo).get()
            .addOnSuccessListener { snapshot ->
                val batch = firebaseDB.batch()
                val snapshotList = snapshot.documents
                snapshotList.forEach { batch.delete(it.reference) }
                batch.commit().addOnSuccessListener { Log.i(TAG, "deleteTransForAcct: $acctNo") }
            }
    }

    fun updateAccount(account: Account) =
        projectsCollectionRef.document(account.accountNumber).set(account)

    fun addTransaction(transaction: Transaction) = transactionsCollectionRef.add(transaction)

}