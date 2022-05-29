package uz.project.currencyconventerapp.domain.repository.impl

import uz.project.currencyconventerapp.data.datasource.CurrencyApi
import uz.project.currencyconventerapp.data.remote.CurrencyResponse
import uz.project.currencyconventerapp.domain.repository.interfaces.MainRepository
import uz.project.currencyconventerapp.utils.Resource
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: CurrencyApi
) : MainRepository {

    override suspend fun getRates(base: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Succes(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }
}