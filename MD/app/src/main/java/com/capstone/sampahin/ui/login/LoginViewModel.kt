package com.capstone.sampahin.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.sampahin.data.login.LoginRepository
import com.capstone.sampahin.data.login.LoginResponse
import com.capstone.sampahin.data.Result
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> get() = _loginResult

    fun findLogin(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Result.Loading
            val result = loginRepository.getLogin(email, password)
            _loginResult.postValue(result)
        }
    }

}
