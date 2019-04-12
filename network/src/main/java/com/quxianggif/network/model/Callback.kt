package com.quxianggif.network.model

import java.lang.Exception

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/12 下午2:48
 * Describe:网络请求响应的回调接口
 */
interface Callback {

    fun onResponse(response:Response)

    fun onFailure(e:Exception)
}