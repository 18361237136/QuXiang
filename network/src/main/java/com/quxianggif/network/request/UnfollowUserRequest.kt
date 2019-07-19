package com.quxianggif.network.request

import com.example.core.GifFun
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.UnfollowUser
import com.quxianggif.network.util.NetworkConst
import okhttp3.Headers

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/7/19 下午1:52
 * Describe:取关用户请求，对应服务器接口:/user/unfollow
 */
class UnfollowUserRequest : Request() {

    private var followingId: Long = 0

    fun followingId(followingId:Long):UnfollowUserRequest{
        this.followingId = followingId
        return this
    }

    override fun url(): String {
        return URL
    }

    override fun method(): Int {
        return POST
    }

    override fun listen(callback: Callback?) {
        setListener(callback)
        inFlight(UnfollowUser::class.java)
    }

    override fun params(): Map<String, String>? {
        val params=HashMap<String,String>()
        if(buildAuthParams(params)){
            params[NetworkConst.FOLLOWING_ID]=followingId.toString()
            return params
        }
        return super.params()
    }

    override fun headers(builder: Headers.Builder): Headers.Builder {
        buildAuthHeaders(builder,NetworkConst.FOLLOWING_ID,NetworkConst.TOKEN)
        return super.headers(builder)
    }

    companion object{
        private val URL = GifFun.BASE_URL + "/user/unfollow"
    }

}