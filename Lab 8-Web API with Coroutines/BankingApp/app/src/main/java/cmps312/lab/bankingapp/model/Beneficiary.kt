package cmps312.lab.bankingapp.model

import kotlinx.serialization.Serializable

@Serializable
class Beneficiary(val name: String, val accountNo: String, val cid: Int = 10001)
