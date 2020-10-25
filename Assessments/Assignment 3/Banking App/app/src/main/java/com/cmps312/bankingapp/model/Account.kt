package com.cmps312.bankingapp.model

import kotlinx.serialization.Serializable

@Serializable
class Account(var accountNo: String, var name: String, var acctType: String, var balance: Int)
