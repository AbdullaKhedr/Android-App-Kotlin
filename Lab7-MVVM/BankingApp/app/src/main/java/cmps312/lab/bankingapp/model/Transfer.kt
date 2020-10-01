package cmps312.lab.bankingapp.model
import kotlinx.serialization.Serializable

@Serializable
class Transfer(var fromAccountNo: String, var amount: Double,
               var beneficiaryName: String = "", var beneficiaryAccountNo: String = "")