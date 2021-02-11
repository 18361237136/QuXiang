package com.quxianggif.network.request

import com.example.core.GifFun
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.PhoneLogin
import com.quxianggif.network.util.NetworkConst

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/24 下午2:26
 * Describe:使用手机号登录请求。对应服务器接口:/login/phone
 */
class PhoneLoginRequest :Request(){

    companion object {
        private val URL = GifFun.BASE_URL + "/login/phone"
    }

    private var number:String=""

    private var code:String=""

    fun number(number:String):PhoneLoginRequest{
        this.number=number
        return this
    }

    fun code(code:String):PhoneLoginRequest{
        this.code=code
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
        inFlight(PhoneLogin::class.java)
    }

    override fun params(): Map<String, String>? {
        val params=HashMap<String,String>()
        params[NetworkConst.NUMBER] = number
        params[NetworkConst.CODE] = code
        params[NetworkConst.DEVICE_NAME] = deviceName
        params[NetworkConst.DEVICE_SERIAL] = deviceSerial
        return params
    }
}