package com.quxianggif.network.request

import com.example.core.GifFun
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.FetchUserFeeds
import com.quxianggif.network.util.NetworkConst
import okhttp3.Headers

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/7/18 下午6:29
 * Describe:获取指定用户所发Feeds的请求，对应服务器接口：/feeds/user
 */
class FetchUserFeedsRequest : Request() {

    private var lastFeed: Long = 0

    private var userId: Long = 0

    fun userId(userId: Long): FetchUserFeedsRequest {
        this.userId = userId
        return this
    }

    fun lastFeed(lastFeed: Long): FetchUserFeedsRequest {
        this.lastFeed = lastFeed
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
        inFlight(FetchUserFeeds::class.java)
    }

    override fun params(): Map<String, String>? {

        val params=HashMap<String,String>()
        if(buildAuthParams(params)){
            if(lastFeed>0){
                params[NetworkConst.LAST_FEED]=lastFeed.toString()
            }
            if (userId > 0) {
                params[NetworkConst.USER_ID] = userId.toString()
            }
            return params
        }
        return super.params()
    }

    override fun headers(builder: Headers.Builder): Headers.Builder {
        buildAuthHeaders(builder,NetworkConst.TOKEN,NetworkConst.USER_ID)
        return super.headers(builder)
    }

    companion object {

        private val URL = GifFun.BASE_URL + "/feeds/user"
    }
}