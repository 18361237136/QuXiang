package com.example.main.event

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/14 上午11:56
 * Describe:发布评论的事件消息。
 */
class PostCommentEvent: MessageEvent() {
    var commentId: Long = 0

    var feedId: Long = 0
}