package com.starlight.dot.framework.utils

import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import android.provider.Settings
import android.util.Log

object DeviceExt {
    private const val TAG = "DeviceExt==>";

   /**
    * 获取手机的deviceId
    * create by Administrator at 2021/4/20 17:45
    * @author Administrator
    * @since 1.0.0
    * @return
    *       手机DeviceId
    */
    @Suppress("DEPRECATION")
    fun deviceId(context: Context): String {

        var id: String = ""
        try {
            id = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } catch (e: Exception) {
            e.printStackTrace();
        }
        return id
    }
}

fun Context.deviceIp() = intToIp((this.getSystemService(WIFI_SERVICE) as WifiManager).connectionInfo.ipAddress)

private fun intToIp(ip: Int) =
    "${(ip and 0xFF)}.${(ip shr 8 and 0xFF)}.${(ip shr 16 and 0xFF)}.${(ip shr 24 and 0xFF)}"
