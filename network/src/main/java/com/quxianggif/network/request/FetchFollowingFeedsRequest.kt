package com.quxianggif.network.request

import com.example.core.GifFun
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.FetchFollowingFeeds
import com.quxianggif.network.util.NetworkConst
import okhttp3.Headers

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/13 下午7:00
 * Describe:获取用户所关注的人所发Feeds的请求。对应服务器接口：/feeds/followings
 */
class FetchFollowingFeedsRequest:Request(){

    private var lastFeed:Long=0

    fun lastFeed(lastFeed:Long):FetchFollowingFeedsRequest{
        this.lastFeed=lastFeed
        return this
    }

    override fun method(): Int {
        return GET
    }

    override fun url(): String {
        return URL
    }

    override fun listen(callback: Callback?) {
        setListener(callback)
        inFlight(FetchFollowingFeeds::class.java)
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
        buildAuthHeaders(builder,NetworkConst.TOKEN,NetworkConst.UID, NetworkConst.DEVICE_SERIAL)
        return super.headers(builder)
    }

    companion object {

        private val URL = GifFun.BASE_URL + "/feeds/followings"
    }

}