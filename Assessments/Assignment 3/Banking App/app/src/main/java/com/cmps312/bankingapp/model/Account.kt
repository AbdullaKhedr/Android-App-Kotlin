package com.cmps312.bankingapp.model

import kotlinx.serialization.Serializable

@Serializable
class Account(var accountNo: String, val name: String, val acctType: String, val balance: Int)
