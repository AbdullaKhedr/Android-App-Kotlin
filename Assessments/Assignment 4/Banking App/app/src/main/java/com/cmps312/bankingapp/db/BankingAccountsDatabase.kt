package com.cmps312.bankingapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cmps312.bankingapp.entity.Account
import com.cmps312.bankingapp.entity.Transaction

@Database(entities = [Account::class, Transaction::class], version = 1, exportSchema = false)
abstract class BankingAccountsDatabase : RoomDatabase() {
    abstract fun bankingAccountsDao(): BankingAccountsDao

    companion object {
        @Volatile
        private var database: BankingAccountsDatabase? = null
        private const val DB_NAME = "BankingAccounts.db"

        @Synchronized
        fun getDatabase(context: Context): BankingAccountsDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    BankingAccountsDatabase::class.java,
                    DB_NAME
                ).build()
            }
            return database as BankingAccountsDatabase
        }
    }
}