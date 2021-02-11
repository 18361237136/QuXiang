package com.quxianggif.network.util

import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import com.example.core.GifFun
import com.example.core.extension.logWarn
import com.example.core.util.GlobalUtil
import com.example.core.util.ShareUtil
import java.lang.Exception
import java.util.*

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/12 下午3:47
 * Describe:获取各项基础数据的工具类
 */
object Utility {

    private val TAG = "Utility"

    private var deviceSerial: String? = null

    val deviceName: String
        get() {
            var deviceName = Build.BRAND + " " + Build.MODEL
            if (TextUtils.isEmpty(deviceName)) {
                deviceName = "unKnown"
            }
            return deviceName
        }

    //获取当前app的版本号
    val appVersion: String
        get() {
            var version = ""
            try {
                val packageManager = GifFun.getContext().packageManager
                val packInfo = packageManager.getPackageInfo(GifFun.getPackageName(), 0)
                version = packInfo.versionName
            } catch (e: Exception) {
                logWarn("getAppVersion", e.message, e)
            }

            if (TextUtils.isEmpty(version)) {
                version = "unKnown"
            }
            return version
        }

    //获取App网络请求验证参数，用于辨识是不是官方渠道的App。
    val appSign: String
        get() {
            return MD5.encrypt(SignUtil.getAppSignature() + appVersion)
        }

    /**
     * 获取设备的序列号。如果无法获取到设备的序列号则会生成一个随机的UUID来作为设备的序列号，UUID生成之后会存入缓存，
     * 下次获取设备序列号的时候会优先从缓存中读取
     */

    @SuppressLint("HardwareIds")
    fun getDeviceSerial(): String {
        if (deviceSerial == null) {
            var deviceId: String? = null
            val appChannel = GlobalUtil.getApplicationMetaData("APP_CHANNEL")
            if ("google" != appChannel || "samsung" != appChannel) {
                try {
                    deviceId = Settings.Secure.getString(GifFun.getContext().contentResolver, Settings.Secure.ANDROID_ID)
                } catch (e: Exception) {
                    logWarn(TAG, "get android_id with error", e)
                }
                if (!TextUtils.isEmpty(deviceId) && deviceId!!.length < 255) {
                    deviceSerial = deviceId
                    return deviceSerial.toString()
                }
            }
            var uuid = ShareUtil.read(NetworkConst.UUID, "")
            if (!TextUtils.isEmpty(uuid)) {
                deviceSerial = uuid
                return deviceSerial.toString()
            }
            uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase()
            ShareUtil.save(NetworkConst.UUID, uuid)
            deviceSerial = uuid
            return deviceSerial.toString()

        } else {
            return deviceSerial.toString()
        }
    }

}