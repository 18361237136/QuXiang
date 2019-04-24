package com.quxianggif.network.request

import com.example.core.GifFun
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.GetBaseInfo
import com.quxianggif.network.util.NetworkConst
import okhttp3.Headers
import java.util.HashMap

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/24 下午3:28
 * Describe:获取当前用户的基本信息请求。对应服务器的接口:/user/baseinfo
 */
class GetBaseInfoRequest :Request(){

    companion object {

        private val URL = GifFun.BASE_URL + "/user/baseinfo"
    }

    override fun method(): Int {
        return Request.GET
    }

    override fun url(): String {
        return URL
    }

    override fun listen(callback: Callback?) {
        setListener(callback)
        inFlight(GetBaseInfo::class.java)
    }

    override fun params(): Map<String, String>? {
        val params = HashMap<String, String>()
        return if (buildAuthParams(params)) {
            params
        } else super.params()
    }

    override fun headers(builder: Headers.Builder): Headers.Builder {
        buildAuthHeaders(builder, NetworkConst.DEVICE_SERIAL, NetworkConst.TOKEN)
        return super.headers(builder)
    }


}