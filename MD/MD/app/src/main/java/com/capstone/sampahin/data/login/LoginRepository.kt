package com.capstone.sampahin.data.login

import com.capstone.sampahin.data.api.ApiService
import retrofit2.HttpException
import com.capstone.sampahin.data.Result
import com.capstone.sampahin.data.TokenPreferences
import com.capstone.sampahin.data.database.UserDao
import com.capstone.sampahin.data.database.UserEntity

class LoginRepository(
    private val apiService: ApiService,
    private val tokenPref : TokenPreferences,
    private val userDao: UserDao
) {

    suspend fun getLogin(email: String, password: String): Result<LoginResponse> {
        return try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)

            val token = response.token ?: return Result.Error("Login failed: UID missing")
            tokenPref.saveToken(token)

            val userEntity = UserEntity(
                response.token,
                response.username ?: "",
                response.email ?: "",
            )
            userDao.insertUser(userEntity)
            Result.Success(response)

        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: "HTTP error occurred"
            Result.Error(errorMessage)
        } catch (e: Exception) {
            Result.Error(e.message ?: "An unexpected error occurred")
        }
    }

    suspend fun getUser(uid: String): UserEntity {
        return userDao.getUserByUid(uid)
    }

    companion object {
        @Volatile
        private var instance: LoginRepository? = null

        fun getInstance(
            apiService: ApiService,
            tokenPref: TokenPreferences,
            userDao: UserDao
        ): LoginRepository =
            instance ?: synchronized(this) {
                instance ?: LoginRepository(apiService, tokenPref, userDao).also { instance = it }
            }
    }
}

