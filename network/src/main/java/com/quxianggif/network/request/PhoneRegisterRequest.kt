package com.quxianggif.network.request

import com.example.core.GifFun
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.PhoneRegister

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/25 下午2:12
 * Describe:注册手机号登录账号的实体类封装
 */
class PhoneRegisterRequest :Request(){

    private var number:String=""

    private var code:String=""

    private var nickname:String=""

    fun number(number:String):PhoneRegisterRequest{
        this.number=number
        return this
    }

    fun code(code: String): PhoneRegisterRequest {
        this.code = code
        return this
    }

    fun nickname(nickname: String): PhoneRegisterRequest {
        this.nickname = nickname
        return this
    }

    override fun method(): Int {
        return POST
    }

    override fun url(): String {
        return URL
    }

    override fun listen(callback: Callback?) {
        setListener(callback)
        inFlight(PhoneRegister::class.java)
    }

    companion object {

        private val URL = GifFun.BASE_URL + "/register/phone"
    }
}