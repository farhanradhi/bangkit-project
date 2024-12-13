package com.capstone.sampahin.ui.maps

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.sampahin.data.Injection
import com.capstone.sampahin.data.login.LoginRepository
import com.capstone.sampahin.data.maps.MapsRepository
import com.capstone.sampahin.ui.login.LoginViewModel
import com.capstone.sampahin.ui.profile.ProfileViewModel

class MapsViewModelFactory private constructor(private val mapsRepository: MapsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(mapsRepository) as T
            }


            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: MapsViewModelFactory? = null
        fun getInstance(): MapsViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: MapsViewModelFactory(Injection.provideMapsRepository())
            }.also { instance = it }
    }
}