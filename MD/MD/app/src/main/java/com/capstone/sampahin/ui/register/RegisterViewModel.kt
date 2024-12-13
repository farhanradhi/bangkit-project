package com.capstone.sampahin.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.capstone.sampahin.data.Result
import com.capstone.sampahin.data.register.RegisterRepository
import com.capstone.sampahin.data.register.RegisterResponse

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {

    val registerResult: LiveData<Result<RegisterResponse>> = registerRepository.registerResult

    fun findRegister(username: String, email: String, password: String) {
        viewModelScope.launch {
            registerRepository.getRegister(username, email, password)
        }
    }
}
