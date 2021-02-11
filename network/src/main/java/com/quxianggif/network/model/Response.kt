package com.quxianggif.network.model

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/12 下午2:27
 * Describe:请求响应的基类，这里封装了所有请求都必须会响应的参数,status和msg
 */
open class Response {

    //状态码，这里可以查看所有状态码的含义：https://github.com/sharefunworks/giffun-server#2-状态码
    var status:Int=0

    //请求结果的简单描述
    var msg:String=""
}