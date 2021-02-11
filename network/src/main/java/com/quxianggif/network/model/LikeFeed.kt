package com.quxianggif.network.model

import com.quxianggif.network.request.LikeFeedRequest

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午2:09
 * Describe:Feed点赞请求的实体类封装
 */
class LikeFeed :Response(){
    companion object{

        fun getResponse(feedId:Long,callback:Callback?){
            LikeFeedRequest()
                    .feed(feedId)
                    .listen(callback)
        }
    }
}