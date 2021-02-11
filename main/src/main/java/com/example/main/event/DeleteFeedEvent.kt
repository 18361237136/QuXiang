package com.example.main.event

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午3:56
 * Describe:删除Feed的事件消息。
 */
class DeleteFeedEvent :MessageEvent(){

    var feedId:Long=0

    var type:Int=0

    companion object{
        const val DELETE_FROM_USER_HOME_PAGE = 0

        const val DELETE_FROM_FOLLOWING_PAGE = 1
    }
}