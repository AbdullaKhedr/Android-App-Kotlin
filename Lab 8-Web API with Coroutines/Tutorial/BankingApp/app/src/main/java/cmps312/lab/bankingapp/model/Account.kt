package cmps312.lab.bankingapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Account(val type: String, val accountNo: String, val cid : Int = 10001 ) {
    override fun toString() = "$type - $accountNo"
}
