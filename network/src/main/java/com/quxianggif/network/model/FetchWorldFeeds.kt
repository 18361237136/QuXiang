package com.quxianggif.network.model

import com.example.core.model.WorldFeed
import com.google.gson.annotations.SerializedName
import com.quxianggif.network.request.FetchWorldFeedsRequest

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午3:30
 * Describe: 获取世界频道Feeds请求的实体类封装。
 */
class FetchWorldFeeds :Response(){
    @SerializedName("data")
    var feeds:List<WorldFeed> =ArrayList()

    companion object{
        fun getResponse(callback: Callback) {
            FetchWorldFeedsRequest()
                    .listen(callback)
        }

        fun getResponse(lastFeed: Long, callback: Callback) {
            FetchWorldFeedsRequest()
                    .lastFeed(lastFeed)
                    .listen(callback)
        }
    }
}