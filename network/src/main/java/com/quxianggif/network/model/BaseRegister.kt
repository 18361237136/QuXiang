package com.quxianggif.network.model

import com.google.gson.annotations.SerializedName

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/25 下午2:00
 * Describe:注册请求所使用的通用基类
 */
open class BaseRegister :Response(){

    //用户的账号id
    @SerializedName("user_id")
    var userId:Int=0

    //记录用户的登录身份，token有效期30天
    var token:String=""

    //用户在第三方账号上所使用的头像，如果账号未注册时会返回次参数
    var avatar:String=""
}