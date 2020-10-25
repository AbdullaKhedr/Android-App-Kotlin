package com.cmps312.bankingapp.data.api

import com.cmps312.bankingapp.model.Account
import retrofit2.http.GET

interface AccountService {

    @GET()
    suspend fun getAccounts(): List<Account>

//    @GET("all")
//    suspend fun getAccount(accountNo: String): Account
//
//    @GET("account/{accountNo}")
//    suspend fun getAccount(@Path("accountNo") accountNo : String) : List<Account>
//
//    @POST("accounts")
//    suspend fun addAccount(@Body account: Account) : Account
//
//    @PUT("accounts/{accountNo}")
//    suspend fun updateAccount(@Path("accountNo") accountNo : String, @Body account: Account) : Account
//
//    @DELETE("accounts/{accountNo}")
//    suspend fun deleteAccount(@Path("accountNo") accountNo: String) : String

}