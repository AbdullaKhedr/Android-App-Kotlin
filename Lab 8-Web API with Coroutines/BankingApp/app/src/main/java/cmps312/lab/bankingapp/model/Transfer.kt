package cmps312.lab.bankingapp.model

import kotlinx.serialization.Serializable

@Serializable
class Transfer(
    var fromAccountNo: String,
    var amount: Double,
    var beneficiaryName: String = "",
    var beneficiaryAccountNo: String = "",
    val cid: Int = 10001,
    var transferId: String = ""
) {
    override fun toString(): String {
        return "Transfer $amount from $fromAccountNo to $beneficiaryName ($beneficiaryAccountNo)"
    }
}