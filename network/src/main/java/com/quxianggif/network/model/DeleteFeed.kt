package com.quxianggif.network.model

import com.quxianggif.network.request.DeleteFeedRequest

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/13 下午6:30
 * Describe:删除Feed请求的实体类封装。
 */
class DeleteFeed :Response() {

    companion object{
        fun getResponse(feedId:Long,callback:Callback){
            DeleteFeedRequest()
                    .feed(feedId)
                    .listen(callback)
        }
    }
}