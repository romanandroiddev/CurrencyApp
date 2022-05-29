package uz.project.currencyconventerapp.data.remote

data class CurrencyResponse(
    val base: String,
    val date: String,
    val rates: Rates
)