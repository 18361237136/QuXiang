package com.quxianggif.network.request

import com.example.core.GifFun
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.FetchVCode
import com.quxianggif.network.util.NetworkConst
import okhttp3.Headers

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/24 上午10:20
 * Describe:获取短信验证码请求。对应服务器接口:/login/fetch_verify_code
 */
class FetchVCodeRequest :Request(){

    companion object {
        private val URL=GifFun.BASE_URL+"/login/fetch_verify_code"
    }

    private var number=""

    fun number(number:String):FetchVCodeRequest{
        this.number=number
        return this
    }

    override fun method(): Int {
        return Request.POST
    }

    override fun url(): String {
        return URL
    }

    override fun listen(callback: Callback?) {
        setListener(callback)
        inFlight(FetchVCode::class.java)
    }

    override fun params(): Map<String, String>? {
        val params=HashMap<String,String>()
        params[NetworkConst.NUMBER]=number
        params[NetworkConst.DEVICE_NAME] = deviceName
        params[NetworkConst.DEVICE_SERIAL] = deviceSerial
        return params
    }

    override fun headers(builder: Headers.Builder): Headers.Builder {
        buildAuthHeaders(builder,NetworkConst.DEVICE_NAME,NetworkConst.NUMBER, NetworkConst.DEVICE_SERIAL)
        return super.headers(builder)
    }


}