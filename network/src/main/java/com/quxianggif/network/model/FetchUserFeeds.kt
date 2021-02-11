package com.quxianggif.network.model

import com.example.core.model.UserFeed
import com.google.gson.annotations.SerializedName
import com.quxianggif.network.request.FetchUserFeedsRequest

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/7/18 下午6:26
 * Describe:获取用户所发Feeds请求的实体类封装
 */
class FetchUserFeeds :Response(){

    @SerializedName("user_id")
    var userId: Long = 0

    var nickname: String = ""

    var avatar: String = ""

    @SerializedName("bg_image")
    var bgImage: String = ""

    @SerializedName("feeds_count")
    var feedsCount: Int = 0

    @SerializedName("followings_count")
    var followingsCount: Int = 0

    @SerializedName("followers_count")
    var followersCount: Int = 0

    @SerializedName("is_following")
    var isFollowing: Boolean = false

    var description: String = ""

    @SerializedName("data")
    var feeds: MutableList<UserFeed> = ArrayList()

    companion object{

        fun getResponse(userId:Long,callback: Callback){
            FetchUserFeedsRequest()
                    .userId(userId)
                    .listen(callback)
        }

        fun getResponse(userId: Long,lastFeed: Long,callback: Callback){
            FetchUserFeedsRequest()
                    .userId(userId)
                    .lastFeed(lastFeed)
                    .listen(callback)
        }
    }
}