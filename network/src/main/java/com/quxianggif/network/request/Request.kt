package com.quxianggif.network.request

import com.quxianggif.network.model.Response
import com.quxianggif.network.util.Utility
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/12 下午2:56
 * Describe:网络请求模式基类，所有的请求封装都应该继承此类。这里会提供网络模块的配置，以及请求的具体逻辑处理等
 */
abstract class Request {

    companion object {
        const val GET = 0

        const val POST = 1

        const val PUT = 2

        const val DELETE = 3
    }

    private lateinit var okHttpClient: OkHttpClient

    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder().addNetworkInterceptor(LoggingInterceptor())

    private var callback: Callback? = null

    private var params: Map<String, String>? = null

    var getParamsAlready = false

    var deviceName: String

    var deviceSerial: String

    init {
        connectTimeout(10)
        writeTimeout(10)
        readTimeout(10)
        deviceName=Utility.deviceName
        deviceSerial=Utility.getDeviceSerial()
    }

    private fun build(){
        okHttpClient=okHttpBuilder.build()
    }

    fun connectTimeout(seconds: Int) {
        okHttpBuilder.connectTimeout(seconds.toLong(), TimeUnit.SECONDS)
    }

    fun writeTimeout(seconds: Int) {
        okHttpBuilder.writeTimeout(seconds.toLong(), TimeUnit.SECONDS)
    }

    fun readTimeout(seconds: Int) {
        okHttpBuilder.readTimeout(seconds.toLong(), TimeUnit.SECONDS)
    }

    //设置响应回调接口
    fun setListener(callback: Callback?){
        this.callback=callback
    }

    //组装网络请求后添加到HTTP发送队列，并监听响应回调
    fun <T:Response> inFlight(requestModel:Class<T>){
        build()
        val requestBuilder=Request.Builder()
        if(method()==GET&&getParams()!=null){

        }
    }

    abstract fun method():Int

    //获取本次请求所携带的所有参数
    private fun getParams():Map<String,String>?{
        if(!getParamsAlready){
            params=params()
            getParamsAlready=true
        }
        return params
    }

    open fun params():Map<String,String>?{
        return null
    }

}
