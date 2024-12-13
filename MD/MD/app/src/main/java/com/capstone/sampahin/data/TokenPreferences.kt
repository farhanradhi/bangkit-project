package com.capstone.sampahin.data

import android.content.Context
import android.content.SharedPreferences

class TokenPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFERENCES_NAME = "login_preferences"
        private const val IS_LOGIN_KEY = "is_login"
        private const val TOKEN_KEY = "token"

        @Volatile
        private var INSTANCE: TokenPreferences? = null

        fun getInstance(context: Context): TokenPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = TokenPreferences(context)
                INSTANCE = instance
                instance
            }
        }
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().apply {
            putBoolean(IS_LOGIN_KEY, true)
            putString(TOKEN_KEY, token)
            apply()
        }
    }

    fun clearToken() {
        sharedPreferences.edit().clear().apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

}