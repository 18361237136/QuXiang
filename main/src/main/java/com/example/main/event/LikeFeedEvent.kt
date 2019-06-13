package com.example.main.event

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 上午11:16
 * Describe:Feed点赞消息时间
 */
class LikeFeedEvent :MessageEvent(){
    var feedId:Long=0

    var likesCount:Int=0

    var type:Int=0

    var from:Int=0

    companion object{
        val LIKE_FEED = 0

        val UNLIKE_FEED = 1

        val FROM_FEED_DETAIL = 0

        val FROM_WORLD = 1

        val FROM_FOLLOWING = 2

        val FROM_HOT = 3

        val FROM_USER_HOME = 4
    }
}