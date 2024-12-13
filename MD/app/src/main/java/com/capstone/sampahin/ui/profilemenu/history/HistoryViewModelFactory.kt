package com.capstone.sampahin.ui.profilemenu.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.sampahin.data.Injection
import com.capstone.sampahin.data.history.HistoryRepository
import com.capstone.sampahin.ui.login.LoginViewModel
import com.capstone.sampahin.ui.profile.ProfileViewModel

class HistoryViewModelFactory private constructor(private val historyRepository: HistoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(historyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: HistoryViewModelFactory? = null
        fun getInstance(): HistoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HistoryViewModelFactory(Injection.provideHistoryRepository())
            }.also { instance = it }
    }
}