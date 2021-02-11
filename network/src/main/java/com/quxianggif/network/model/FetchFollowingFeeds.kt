package com.quxianggif.network.model

import com.example.core.model.FollowingFeed
import com.google.gson.annotations.SerializedName
import com.quxianggif.network.request.FetchFollowingFeedsRequest

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/13 下午6:58
 * Describe:获取用户所关注的人所发Feeds请求的实体类封装。
 */
class FetchFollowingFeeds :Response() {

    @SerializedName("data")
    var feeds: List<FollowingFeed> = ArrayList()

    companion object {

        fun getResponse(callback: Callback) {
            FetchFollowingFeedsRequest()
                    .listen(callback)
        }

        fun getResponse(lastFeed: Long, callback: Callback) {
            FetchFollowingFeedsRequest()
                    .lastFeed(lastFeed)
                    .listen(callback)
        }
    }
}