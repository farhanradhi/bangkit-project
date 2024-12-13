package com.capstone.sampahin.ui.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.sampahin.data.Injection
import com.capstone.sampahin.data.register.RegisterRepository

class RegisterViewModelFactory private constructor(private val registerRepository: RegisterRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(registerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: RegisterViewModelFactory? = null
        fun getInstance(): RegisterViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: RegisterViewModelFactory(Injection.provideRegisterRepository())
            }.also { instance = it }
    }
}