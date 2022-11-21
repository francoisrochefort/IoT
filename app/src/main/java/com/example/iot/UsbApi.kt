package com.example.iot

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbManager
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.hoho.android.usbserial.util.SerialInputOutputManager

class UsbApi(private val context: Context) {

    class NoUsbDriver : Exception()

    fun start(listener: SerialInputOutputManager.Listener) {
        val manager: UsbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager)
        if (availableDrivers.isEmpty())
            throw NoUsbDriver()
        val driver = availableDrivers.first()
        while (!manager.hasPermission(driver.device)) {
            manager.requestPermission(
                driver.device,
                PendingIntent.getBroadcast(
                    context,
                    0,
                    Intent("com.eco_trak.IoT.USB_PERMISSION"),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            Thread.sleep(500)
        }
        val connection = manager.openDevice(driver.device)
        val port: UsbSerialPort = driver.ports.first()
        port.open(connection)
        port.setParameters(BAUD_RATE, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)
        val usbIoManager = SerialInputOutputManager(port, listener)
        usbIoManager.start()
    }
}