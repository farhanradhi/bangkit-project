package com.capstone.sampahin.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.sampahin.data.Result
import com.capstone.sampahin.data.maps.MapsRepository
import com.capstone.sampahin.data.maps.MapsResponses
import kotlinx.coroutines.launch

class MapsViewModel(private val mapsRepository: MapsRepository) : ViewModel() {

    val mapsResult: LiveData<Result<MapsResponses>> = mapsRepository.mapsResult

    fun fetchMapsData(address: String, radius: Int) {
        viewModelScope.launch {
            mapsRepository.getMaps(address, radius)
        }
    }
}
