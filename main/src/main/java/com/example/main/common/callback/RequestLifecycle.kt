package com.example.main.common.callback

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/30 上午11:07
 * Describe:在ActivityFragment中进行网络请求所需要经历的生命周期
 */
interface RequestLifecycle {

    fun startLoading()

    fun loadFinished()

    fun loadFailed(msg:String?)
}