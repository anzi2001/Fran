package com.kockalabs.fran.di

import com.kockalabs.fran.api.FranApi
import com.kockalabs.fran.api.FranRepository
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://kocjancic.info/api/v1/fran/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideFranService(retrofit: Retrofit) : FranApi {
        return retrofit.create(FranApi::class.java)
    }

    @Provides
    fun provideFranRepository(franApi: FranApi) : FranRepository {
        return FranRepository(franApi)
    }
}