package com.quxianggif.network.model

import com.example.core.model.HotFeed
import com.google.gson.annotations.SerializedName
import com.quxianggif.network.request.FetchHotFeedsRequest

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/14 上午11:39
 * Describe:获取热门Feeds请求的实体类封装。
 */
class FetchHotFeeds :Response(){

    @SerializedName("data")
    var feeds:List<HotFeed> =ArrayList()

    companion object{

        fun getResponse(callback: Callback){
            FetchHotFeedsRequest()
                    .listen(callback)
        }

        fun getLoadingMoreResponse(callback: Callback) {
            FetchHotFeedsRequest()
                    .isLoadingMore(true)
                    .listen(callback)
        }
    }
}