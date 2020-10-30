package cmps312.lab.beneficiaryreivewlab.model

import kotlinx.serialization.Serializable

@Serializable
data class Beneficiary(var name : String = "", var accountNo : String="", var cid : Int =0  , var id : String = "")
