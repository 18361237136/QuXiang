package com.quxianggif.network.request

import com.example.core.GifFun
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.LikeFeed
import com.quxianggif.network.util.NetworkConst
import okhttp3.Headers

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午2:13
 * Describe:Feed点赞请求
 */
class LikeFeedRequest :Request(){

    private var feed:Long=0

    fun feed(feed:Long):LikeFeedRequest{
        this.feed=feed
        return this
    }

    override fun url(): String {
        return URL
    }

    override fun method(): Int {
        return Request.POST
    }

    override fun listen(callback: Callback?) {
        setListener(callback)
        inFlight(LikeFeed::class.java)
    }

    override fun params(): Map<String, String>? {
        val params=HashMap<String,String>()
        if(buildAuthParams(params)){
            params[NetworkConst.FEED]=feed.toString()
            return params
        }
        return super.params()
    }

    override fun headers(builder: Headers.Builder): Headers.Builder {
        buildAuthHeaders(builder,NetworkConst.FEED,NetworkConst.TOKEN)
        return super.headers(builder)
    }

    companion object {

        private val URL = GifFun.BASE_URL + "/feeds/like"
    }
}