package com.quxianggif.network.request

import com.example.core.GifFun
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.FetchHotFeeds
import com.quxianggif.network.util.NetworkConst
import okhttp3.Headers

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/14 上午11:41
 * Describe:获取热门Feeds请求。对应服务器接口：/feeds/hot
 */
class FetchHotFeedsRequest :Request(){

    private var isLoadingMore=false

    fun isLoadingMore(isLoadingMore:Boolean):FetchHotFeedsRequest{
        this.isLoadingMore=isLoadingMore
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
        inFlight(FetchHotFeeds::class.java)
    }

    override fun params(): Map<String, String>? {
        val params=HashMap<String,String>()
        if(buildAuthParams(params)){
            params[NetworkConst.LOADING_MORE]=isLoadingMore.toString()
            return params
        }
        return super.params()
    }

    override fun headers(builder: Headers.Builder): Headers.Builder {
        buildAuthHeaders(builder,NetworkConst.TOKEN, NetworkConst.LOADING_MORE, NetworkConst.DEVICE_SERIAL)
        return super.headers(builder)
    }

    companion object {

        private val URL = GifFun.BASE_URL + "/feeds/hot"
    }

}