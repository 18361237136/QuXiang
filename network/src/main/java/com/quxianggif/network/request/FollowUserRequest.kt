package com.quxianggif.network.request

import com.example.core.GifFun
import com.quxianggif.network.model.Callback
import com.quxianggif.network.model.FollowUser
import com.quxianggif.network.util.NetworkConst
import okhttp3.Headers

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/7/19 下午2:18
 * Describe:关注用户请求。对应服务器接口：/user/follow
 */
class FollowUserRequest :Request(){

    private var followingIds: LongArray? = null

    fun followingIds(vararg followingIds: Long): FollowUserRequest {
        this.followingIds = followingIds
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
        inFlight(FollowUser::class.java)
    }

    override fun params(): Map<String, String>? {
        val params=HashMap<String,String>()
        if(buildAuthParams(params)){
            val followingsBuilder=StringBuilder()
            var needComma=false
            if(this.followingIds!=null&&this.followingIds!!.size>0){
                for(followingId in this.followingIds!!){
                    if(needComma){
                        followingsBuilder.append(",")
                    }
                    followingsBuilder.append(followingId)
                    needComma=true
                }
            }
            params[NetworkConst.FOLLOWING_IDS]=followingsBuilder.toString()
            return params
        }
        return super.params()
    }

    override fun headers(builder: Headers.Builder): Headers.Builder {
        buildAuthHeaders(builder,NetworkConst.FOLLOWING_IDS,NetworkConst.TOKEN)
        return super.headers(builder)
    }

    companion object {
        private val URL = GifFun.BASE_URL + "/user/follow"
    }
}