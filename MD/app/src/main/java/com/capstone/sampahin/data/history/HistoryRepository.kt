package com.capstone.sampahin.data.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.sampahin.data.Result
import com.capstone.sampahin.data.api.MLApiService

class HistoryRepository(
    private val apiService: MLApiService
) {
    fun getHistory(): LiveData<Result<List<HistoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getHistory()
            if (response.history != null) {
                val nonNullHistory = response.history.filterNotNull()
                val sortedHistory = nonNullHistory.sortedByDescending { it.timestamp }
                emit(Result.Success(sortedHistory))
            } else {
                emit(Result.Error("Empty history response"))
            }
        } catch (e: Exception) {
            Log.e("HistoryRepository", "Error fetching history: ${e.message}", e)
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }

    companion object {
        @Volatile
        private var instance: HistoryRepository? = null

        fun getInstance(
            apiService: MLApiService,
        ): HistoryRepository =
            instance ?: synchronized(this) {
                instance ?: HistoryRepository(apiService).also { instance = it }
            }
    }
}