package com.quxianggif.network.model

import com.quxianggif.network.request.FollowUserRequest

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/7/19 下午2:16
 * Describe:关注用户请求的实体类封装
 */
class FollowUser :Response(){

    companion object{

        fun getResponse(userId:Long,callback: Callback){
            FollowUserRequest()
                    .followingIds(userId)
                    .listen(callback)
        }

        fun getResponse(userIds:LongArray,callback: Callback){
            FollowUserRequest()
                    .followingIds(*userIds)
                    .listen(callback)
        }
    }
}