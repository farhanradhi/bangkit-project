package com.capstone.sampahin.data

import android.content.Context
import com.capstone.sampahin.data.api.ApiConfig
import com.capstone.sampahin.data.history.HistoryRepository
import com.capstone.sampahin.data.login.LoginRepository
import com.capstone.sampahin.data.maps.MapsRepository
import com.capstone.sampahin.data.register.RegisterRepository

object Injection {

    fun provideRegisterRepository(): RegisterRepository {
        val apiService = ApiConfig.getApiService()
        return RegisterRepository.getInstance(apiService)
    }

    fun provideLoginRepository(context: Context): LoginRepository {
        val apiService = ApiConfig.getApiService()
        return LoginRepository.getInstance(
            apiService,
            TokenPreferences.getInstance(context),
            AppDatabase.getInstance(context).userDao()
        )
    }

    fun provideMapsRepository():MapsRepository {
        val apiService = ApiConfig.getMapApiService()
        return MapsRepository.getInstance(apiService)
    }

    fun provideHistoryRepository():HistoryRepository {
        val apiService = ApiConfig.getMLApiService()
        return HistoryRepository.getInstance(apiService)
    }

}
