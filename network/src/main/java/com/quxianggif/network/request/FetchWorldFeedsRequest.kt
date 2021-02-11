package com.quxianggif.network.request

import com.example.core.GifFun
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.FetchWorldFeeds
import com.quxianggif.network.util.NetworkConst
import okhttp3.Headers

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午3:32
 * Describe:获取世界频道Feeds请求。对应服务器接口：/feeds/world
 */
class FetchWorldFeedsRequest :Request(){

    private var lastFeed:Long=0

    fun lastFeed(lastFeed:Long):FetchWorldFeedsRequest{
        this.lastFeed=lastFeed
        return this
    }

    override fun url(): String {
        return URL
    }

    override fun method(): Int {
        return Request.GET
    }

    override fun listen(callback: Callback?) {
        setListener(callback)
        inFlight(FetchWorldFeeds::class.java)
    }

    override fun params(): Map<String, String>? {
        val params=HashMap<String,String>()
        if(buildAuthParams(params)){
            if(lastFeed>0){
                params[NetworkConst.LAST_FEED]=lastFeed.toString()
            }
            return params
        }
        return super.params()
    }

    override fun headers(builder: Headers.Builder): Headers.Builder {
        buildAuthHeaders(builder,NetworkConst.UID,NetworkConst.TOKEN)
        return super.headers(builder)
    }

    companion object {

        private val URL = GifFun.BASE_URL + "/feeds/world"
    }

}