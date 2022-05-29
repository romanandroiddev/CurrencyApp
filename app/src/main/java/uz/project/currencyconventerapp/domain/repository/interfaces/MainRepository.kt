package uz.project.currencyconventerapp.domain.repository.interfaces

import uz.project.currencyconventerapp.data.remote.CurrencyResponse
import uz.project.currencyconventerapp.utils.Resource

interface MainRepository {
    suspend fun getRates(base: String): Resource<CurrencyResponse>
}