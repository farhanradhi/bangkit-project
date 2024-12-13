package com.capstone.sampahin.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.sampahin.data.Injection
import com.capstone.sampahin.data.login.LoginRepository

class ProfileViewModelFactory(private val loginRepository: LoginRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModel::class.java)) {
            return ProfileViewModel(loginRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ProfileViewModelFactory? = null

        fun getInstance(context: Context): ProfileViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ProfileViewModelFactory(Injection.provideLoginRepository(context))
            }.also { instance = it }
    }
}
