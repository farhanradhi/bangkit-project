package com.capstone.sampahin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _currentTab = MutableLiveData<String>("Chat")
    val currentTab: LiveData<String> get() = _currentTab

    fun updateTab(tab: String) {
        _currentTab.value = tab
    }
}