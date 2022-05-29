package uz.project.currencyconventerapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.project.currencyconventerapp.data.datasource.CurrencyApi
import uz.project.currencyconventerapp.domain.repository.impl.MainRepositoryImpl
import uz.project.currencyconventerapp.utils.DispatcherProvider
import javax.inject.Singleton


private const val BASE_URL = "https://api.exchangerate.host/"


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @[Singleton Provides]
    fun provideCurrencyApi(): CurrencyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyApi::class.java)


    @[Singleton Provides]
    fun provideMainRepository(api: CurrencyApi) = MainRepositoryImpl(api)



    @[Singleton Provides]
    fun provideDisthpatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfirmed: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}