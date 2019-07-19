package com.quxianggif.network.model

import com.quxianggif.network.request.UnfollowUserRequest

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/7/19 下午1:49
 * Describe:取消关注用户请求的额实体类
 */
class UnfollowUser :Response(){

    companion object{

        fun getResponse(followingId:Long,callback: Callback){
            UnfollowUserRequest()
                    .followingId(followingId)
                    .listen(callback)
        }
    }
}