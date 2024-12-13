package com.capstone.sampahin.data.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.sampahin.data.Result
import com.capstone.sampahin.data.api.ApiService

class RegisterRepository(private val apiService: ApiService) {

    private val _registerResult = MutableLiveData<Result<RegisterResponse>>()
    val registerResult: LiveData<Result<RegisterResponse>> get() = _registerResult

    suspend fun getRegister(username: String, email: String, password: String) {
        _registerResult.postValue(Result.Loading)
        try {
            val bodyReq = RegisterRequest(username, email, password)
            val response = apiService.register(bodyReq)
            _registerResult.postValue(Result.Success(response))
        } catch (e: Exception) {
            _registerResult.postValue(Result.Error(e.message ?: "Terjadi kesalahan"))
        }
    }

    companion object {
        @Volatile
        private var instance: RegisterRepository? = null

        fun getInstance(apiService: ApiService): RegisterRepository =
            instance ?: synchronized(this) {
                instance ?: RegisterRepository(apiService)
            }.also { instance = it }
    }
}
