package cmps312.lab.bankingapp.data.api




import cmps312.lab.bankingapp.model.Account
import cmps312.lab.bankingapp.model.Beneficiary
import cmps312.lab.bankingapp.model.Transfer
import retrofit2.http.*

interface BankService {

    @GET("accounts/{cid}")
    suspend fun getAccounts(@Path("cid") cid : Int) : List<Account>

    @GET("transfers/{cid}")
    suspend fun getTransfer(@Path("cid") cid : Int) : List<Transfer>

    @POST("transfers/{cid}")
    suspend fun addTransfer(@Path("cid") cid : Int , @Body transfer: Transfer) : Transfer

    @DELETE("transfers/{cid}/{transferId}")
    suspend fun deleteTransfer(@Path("cid")cid : Int ,
                       @Path("transferId")transferId : String) : String

    @GET("beneficiaries/{cid}")
    suspend fun getBeneficiaries(@Path("cid") cid : Int) : List<Beneficiary>

    @POST("beneficiaries/{cid}")
    suspend fun addBeneficiaries(@Path("cid") cid : Int ,
                                 @Body beneficiary: Beneficiary) : Beneficiary

    @PUT("beneficiaries/{cid}")
    suspend fun updateBeneficiaries(@Path("cid") cid : Int ,
                                    @Body beneficiary: Beneficiary) : String

    @DELETE("beneficiaries/{cid}/{accountNo}")
    suspend fun deleteBeneficiaries(@Path("cid") cid : Int,
                                    @Path("accountNo") accountNo : String) : String
}

