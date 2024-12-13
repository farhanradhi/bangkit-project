package com.capstone.sampahin.ui.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.sampahin.data.api.ApiConfig
import com.capstone.sampahin.data.chat.ChatRequest
import com.capstone.sampahin.data.chat.ChatResponse
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _topics = MutableLiveData<List<String>>()
    val topics: LiveData<List<String>> get() = _topics

    private val _questions = MutableLiveData<List<String>>()
    val questions: LiveData<List<String>> get() = _questions

    private val _answers = MutableLiveData<ChatResponse?>()
    val answers: LiveData<ChatResponse?> get() = _answers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchTopics() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val apiService = ApiConfig.getChatApiService()
                val response = apiService.getTopics()
                _topics.value = response.topics
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching topics: ${e.message}")
                e.printStackTrace()
                _topics.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchQuestionsByTopic(topic: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val apiService = ApiConfig.getChatApiService()
                val response = apiService.getQuestions(topic)
                _questions.value = response.questions
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching questions: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchAnswers(request: ChatRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val apiService = ApiConfig.getChatApiService()
                val answers = apiService.sendAnswer(request)
                _answers.postValue(answers)
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching answers: ${e.message}")
                e.printStackTrace()
                _answers.postValue(null)
            } finally {
                _isLoading.value = false
            }
        }
    }

    companion object {
        private const val TAG = "ChatViewModel"
    }
}