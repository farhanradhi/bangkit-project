package com.capstone.sampahin.ui.profilemenu.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.sampahin.data.Result
import com.capstone.sampahin.data.history.HistoryItem
import com.capstone.sampahin.data.history.HistoryRepository

class HistoryViewModel(private val historyRepository: HistoryRepository): ViewModel() {

    fun getHistory(): LiveData<Result<List<HistoryItem>>> {
        return historyRepository.getHistory()
    }
}