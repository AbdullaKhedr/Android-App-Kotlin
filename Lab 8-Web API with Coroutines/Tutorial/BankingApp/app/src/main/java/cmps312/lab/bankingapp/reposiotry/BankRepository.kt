package cmps312.lab.bankingapp.reposiotry

import android.content.Context
import cmps312.lab.bankingapp.model.Account
import cmps312.lab.bankingapp.model.Beneficiary
import cmps312.lab.bankingapp.model.Transfer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class BankRepository(private val context: Context) {
    private fun readData(filename: String) =
                context.assets.open(filename).bufferedReader().use { it.readText() }

    fun getTransfers() =
        Json.decodeFromString<List<Transfer>>( readData("transfers.json") )

    fun getBeneficiaries() =
        Json.decodeFromString<List<Beneficiary>>( readData("beneficiaries.json") )

    fun getAccounts() =
        Json.decodeFromString<List<Account>> ( readData("accounts.json") )
}