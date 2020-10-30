package cmps312.lab.beneficiaryreivewlab.data.repository

import cmps312.lab.beneficiaryreivewlab.data.api.BeneficiaryService
import cmps312.lab.beneficiaryreivewlab.model.Beneficiary
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object BeneficiaryRepo {
    val cid = 10001

    private val BASE_URL = "https://cmps312banking.herokuapp.com/api/"
    val contentType = "application/json".toMediaType()
    val converterFactory = Json { ignoreUnknownKeys = true }
        .asConverterFactory(contentType)

    val beneficiaryService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
            .create(BeneficiaryService::class.java)
    }

    suspend fun getBeneficiaries() = beneficiaryService.getBeneficiaries(cid)
    suspend fun addBeneficiary(beneficiary: Beneficiary) = beneficiaryService.addBeneficiary(cid, beneficiary)
    suspend fun deleteBeneficiary(accountNo : String) = beneficiaryService.deleteBeneficiary(cid, accountNo)
}
