package com.example.core.util

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.widget.Toast
import com.example.core.GifFun
import com.example.core.extension.logWarn
import java.text.SimpleDateFormat
import java.util.*

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/11 下午4:31
 * Describe:应用程序全局的通用工具类，功能比较单一，经常被复用的功能，应该封装到此工具类中，从而给全局代码提供方面的操作
 */
object GlobalUtil {
    private val TAG = "GlobalUtil"

    private var toast: Toast? = null

    //获取当前应用程序的包名
    val appPackage: String
        get() = GifFun.getContext().packageName

    //获取当前应用程序的名称
    val appName: String
        get() = GifFun.getContext().resources.getString(GifFun.getContext().applicationInfo.labelRes)

    //获取当前应用程序的版本名
    val appVersionName: String
        get() = GifFun.getContext().packageManager.getPackageInfo(appPackage, 0).versionName

    //获取当前应用程序的版本号
    val appVersionCode: Int
        get() = GifFun.getContext().packageManager.getPackageInfo(appPackage, 0).versionCode

    //获取当前时间的字符串，格式为yyyyMMddHHmmss
    val currentDateString: String
        get() {
            val sdf=SimpleDateFormat("yyyyMMddHHmmss", Locale.US)
            return sdf.format(Date())
        }

    //将当前线程睡眠指定毫秒数
    fun sleep(millis:Long){
        try {
            Thread.sleep(millis)
        }catch (e:InterruptedException){
            e.printStackTrace()
        }
    }

    fun getApplicationMetaData(key:String):String?{
        var applicationInfo:ApplicationInfo?=null
        try {
            applicationInfo=GifFun.getContext().packageManager.getApplicationInfo(appPackage, PackageManager.GET_META_DATA)
        }catch (e:PackageManager.NameNotFoundException){
            logWarn(TAG,e.message,e)
        }
        if(applicationInfo==null) return ""
        return applicationInfo.metaData.getString(key)
    }
}