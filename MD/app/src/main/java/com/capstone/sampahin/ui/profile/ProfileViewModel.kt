package com.capstone.sampahin.ui.profile

import androidx.lifecycle.ViewModel
import com.capstone.sampahin.data.database.UserEntity
import com.capstone.sampahin.data.login.LoginRepository

class ProfileViewModel(private val repository: LoginRepository) : ViewModel(){
    suspend fun getUser(uid: String): UserEntity {
        return repository.getUser(uid)
    }
}