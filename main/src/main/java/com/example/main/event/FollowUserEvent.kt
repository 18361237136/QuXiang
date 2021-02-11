package com.example.main.event

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/7/19 上午11:42
 * Describe:关注和取消关注用户的事件消息
 *
 */
class FollowUserEvent :MessageEvent() {

    var userId:Long=0

    var type: Int = 0

    companion object {

        val FOLLOW_USER = 0

        val UNFOLLOW_USER = 1
    }
}