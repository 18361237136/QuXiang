package com.quxianggif.network.request

import com.example.core.GifFun
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.DeleteFeed
import com.quxianggif.network.util.NetworkConst
import okhttp3.Headers

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/13 下午6:32
 * Describe:删除Feed请求。对应服务器接口：/feeds/delete
 */
class DeleteFeedRequest :Request(){

    private var feed:Long=0

    fun feed(feed:Long):DeleteFeedRequest{
        this.feed=feed
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
        inFlight(DeleteFeed::class.java)
    }

    override fun params(): Map<String, String>? {
        val params=HashMap<String,String>()
        if (buildAuthParams(params)) {
            params[NetworkConst.FEED] = feed.toString()
            return params
        }
        return super.params()
    }

    override fun headers(builder: Headers.Builder): Headers.Builder {
        buildAuthHeaders(builder, NetworkConst.TOKEN, NetworkConst.DEVICE_SERIAL, NetworkConst.FEED)
        return super.headers(builder)
    }

    companion object {

        private val URL = GifFun.BASE_URL + "/feeds/delete"
    }
}