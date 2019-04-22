package com.example.main.event

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/22 上午10:41
 * Describe:销毁Activity事件
 */
class FinishActivityEvent :MessageEvent() {

    var activityClass:Class<*>?=null
}