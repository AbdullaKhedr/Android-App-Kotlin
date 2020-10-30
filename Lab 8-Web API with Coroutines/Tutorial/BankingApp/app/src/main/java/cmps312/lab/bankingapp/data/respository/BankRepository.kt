package cmps312.lab.bankingapp.data.respository

import cmps312.lab.bankingapp.data.api.BankService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object BankRepository {

    val customerId = 10001
    private const val BASE_URL  =  "https://cmps312banking.herokuapp.com/api/"

    //the server will send us json -> kotlin object
    //we will send the server kotlin -> json object
    private val contentType = "application/json".toMediaType()
    private val convertorFactory = Json {ignoreUnknownKeys = true}.asConverterFactory(contentType)

    val bankService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(convertorFactory)
            .build()
            .create(BankService::class.java)
    }
}