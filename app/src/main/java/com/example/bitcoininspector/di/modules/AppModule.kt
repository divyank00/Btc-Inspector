package com.example.bitcoininspector.di.modules

import com.example.bitcoininspector.data.Repository.MainActivityRepository
import com.example.bitcoininspector.data.network.Api
import com.example.bitcoininspector.util.AppConstants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    // TODO provide App level dependencies in here using @Provides
    @Provides
    @Singleton
    fun provideOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): Api =
        retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun providesMainActivityRepository(
        api: Api,
    ): MainActivityRepository {
        return MainActivityRepository(api)
    }
}
