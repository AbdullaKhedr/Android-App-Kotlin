package cmps312.lab.beneficiaryreivewlab.data.api

import cmps312.lab.beneficiaryreivewlab.model.Beneficiary
import retrofit2.http.*

interface BeneficiaryService {
    @GET("beneficiaries/{cid}")
    suspend fun getBeneficiaries(@Path("cid") cid : Int) : List<Beneficiary>

    @DELETE("beneficiaries/{cid}/{accountNo}")
    suspend fun deleteBeneficiary(@Path("cid") cid : Int, @Path("accountNo") accountNo : String) : String

    @POST("beneficiaries/{cid}")
    suspend fun addBeneficiary(@Path("cid") cid : Int, @Body beneficiary: Beneficiary) : Beneficiary

    @PUT("beneficiaries/{cid}")
    suspend fun updateBeneficiary(@Path("cid") cid : Int, @Body beneficiary: Beneficiary) : Beneficiary
}