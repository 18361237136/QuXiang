package com.example.main.event

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/14 上午11:57
 * Describe:删除评论的事件消息。
 */
class DeleteCommentEvent : MessageEvent(){

    var commentId: Long = 0

    var feedId: Long = 0
}