package cmps312.lab.bankingapp.model
import kotlinx.serialization.Serializable

@Serializable
class Transfer(var fromAccountNo: String = "", var amount: Double = 0.0,
               var beneficiaryName: String = "", var beneficiaryAccountNo: String = "")