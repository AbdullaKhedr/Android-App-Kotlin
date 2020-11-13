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
        firebaseDB .firestoreSettings = firestoreSettings { isPersistenceEnabled = true }
    }
    suspend fun getAccounts() = projectsCollectionRef.get().await().toObjects(Account::class.java)
    fun addAccount(account: Account) = projectsCollectionRef.add(account).addOnCompleteListener {
        if (it.isSuccessful) {
            Log.i(TAG, "addAccount: Successful")
        } else {
            Log.i(TAG, "addAccount: Fail")
        }
    }

    fun deleteAccount(account: Account) =
        projectsCollectionRef.document(account.accountNumber).delete()

    fun updateAccount(account: Account) =
        projectsCollectionRef.document(account.accountNumber).set(account)

    fun addTransaction(transaction: Transaction) = transactionsCollectionRef.add(transaction)

    /*

    fun getAccounts(type: String) = bankingAccountsDao.getAccounts(type)
    fun getAllAccounts() = bankingAccountsDao.getAllAccounts()

    //    suspend fun addAccount(account: Account) = bankingAccountsDao.addAccount(account)
    suspend fun updateAccount(account: Account) = bankingAccountsDao.updateAccount(account)

    //    suspend fun deleteAccount(account: Account) = bankingAccountsDao.deleteAccount(account)
    suspend fun addTransaction(transaction: Transaction) =
        bankingAccountsDao.addTransaction(transaction)

     */
}