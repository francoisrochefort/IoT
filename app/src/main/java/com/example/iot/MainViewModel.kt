package com.example.iot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

// PATCH
object GlobalUsbRepository {
    var debugString = mutableStateOf("There is no spoon")
}

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: UsbRepository) : ViewModel() {

//    var debugString by mutableStateOf("Reading USB port...")
    var debugString by GlobalUsbRepository.debugString
        private set

    init {
        viewModelScope.launch {
            repo.eventFlow().collect { event ->
                when (event) {
                    is UsbEvent.OnOutputDebugString -> {
//                        debugString = event.debugString
                        GlobalUsbRepository.debugString.value = event.debugString
                    }
                }
            }
        }
    }

}