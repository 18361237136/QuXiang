package com.quxianggif.network.request

import com.example.core.GifFun
import com.example.core.util.GlobalUtil
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.Init
import com.quxianggif.network.util.NetworkConst
import okhttp3.Headers

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/12 下午2:52
 * Describe:初始化请求。对应服务器接口:/init
 */
class InitRequest :Request(){

    companion object {
        private val URL=GifFun.BASE_URL+"/init"
    }

    init {
        connectTimeout(5)
        readTimeout(5)
        writeTimeout(5)
    }

    override fun url(): String {
        return URL
    }

    override fun listen(callback: Callback?) {
        setListener(callback)
        inFlight(Init::class.java)
    }

    override fun method(): Int {
        return Request.GET
    }

    override fun params(): Map<String, String>? {
        val params=HashMap<String,String>()
        params[NetworkConst.CLIENT_VERSION]=GlobalUtil.appVersionCode.toString()
        val appChannel=GlobalUtil.getApplicationMetaData("APP_CHANNEL")
        if(appChannel!=null){
            params[NetworkConst.CLIENT_VERSION]=appChannel
        }
        if(buildAuthParams(params)){
            params[NetworkConst.DEVICE_NAME] = deviceName
        }
        return params
    }

    override fun headers(builder: Headers.Builder): Headers.Builder {
        buildAuthHeaders(builder,NetworkConst.UID,NetworkConst.TOKEN)
        return super.headers(builder)
    }

}