package uz.project.currencyconventerapp.data.datasource

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.project.currencyconventerapp.data.remote.CurrencyResponse

interface CurrencyApi {
    @GET("/latest")
    suspend fun getRates(
        @Query("base") base: String
    ): Response<CurrencyResponse>
}