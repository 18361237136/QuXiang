package com.quxianggif.network.exception

import java.lang.RuntimeException

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/15 下午2:23
 * Describe:当服务器响应的头不在200与300之间时，说明请求出现了异常，此时应该将此异常主动抛出。
 */
class ResponseCodeException(val responseCode:Int):RuntimeException("Http request failed with response code $responseCode")