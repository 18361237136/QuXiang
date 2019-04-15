package com.quxianggif.network.model

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/15 下午2:03
 * Describe:网络请求响应的回调接口，回调时保留原来线程进行回调，不切换到主线程
 */
interface OriginThreadCallback :Callback