package cmps312.lab.bankingapp.reposiotry

import android.content.Context
import cmps312.lab.bankingapp.model.Account
import cmps312.lab.bankingapp.model.Beneficiary
import cmps312.lab.bankingapp.model.Transfer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object BankRepository {
    private fun readData(filename: String, context: Context) = context.assets
        .open(filename)
        .bufferedReader().use { it.readText() }

    fun getTransfers(context: Context) =
        Json.decodeFromString<List<Transfer>>( readData("transfers.json", context) )

    fun getBeneficiaries(context: Context) =
        Json.decodeFromString<List<Beneficiary>>( readData("beneficiaries.json", context) )

    fun getAccounts(context: Context) =
        Json.decodeFromString<List<Account>> ( readData("accounts.json", context) )
}