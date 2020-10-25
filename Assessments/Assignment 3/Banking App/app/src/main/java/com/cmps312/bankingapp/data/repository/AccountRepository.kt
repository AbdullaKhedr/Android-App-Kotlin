package com.cmps312.bankingapp.data.repository

import com.cmps312.bankingapp.data.api.AccountService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object AccountRepository {

    private const val BASE_URL = "https://employee-bank-app.herokuapp.com/api/accounts/"
    private val contentType = "application/json".toMediaType()
    private val jsonConverterFactory = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }.asConverterFactory(contentType)


    private val accountService: AccountService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConverterFactory)
            .build()
            .create(AccountService::class.java)
    }

    suspend fun getAccounts() = accountService.getAccounts()

}