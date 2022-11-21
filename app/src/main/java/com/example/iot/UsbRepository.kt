package com.example.iot

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.hoho.android.usbserial.util.SerialInputOutputManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.json.JSONObject

/*
{
    "command": "outputDebugString",
    "parameters":
    {
        [
            "debugString": "There is no spoon!"
        ]
    }
}
*/

sealed class UsbEvent {
    data class OnOutputDebugString(val debugString: String) : UsbEvent()
}

class UsbRepository(private val usbApi: UsbApi) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun eventFlow() = callbackFlow<UsbEvent> {

        val listener = object : SerialInputOutputManager.Listener {

            override fun onNewData(data: ByteArray?) {
                if (data != null) {
                    trySend(UsbEvent.OnOutputDebugString(debugString = String(data)))
                }
            }

            override fun onRunError(e: Exception?) {
                Log.d(TAG, "onRunError(${e?.message})")
            }

        }
        usbApi.start(listener)
        awaitClose {
        }
    }
}