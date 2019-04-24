package com.quxianggif.network.model

import com.google.gson.annotations.SerializedName
import com.quxianggif.network.model.Response
import com.quxianggif.network.request.PhoneLoginRequest

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/24 下午2:19
 * Describe:手机号登录的实体类封装
 */
class PhoneLogin :Response(){

    @SerializedName("user_id")
    var userId:Long=0


    //记录用户的登录身份，token有效期30天
    var token=""

    companion object {
        fun getResponse(number:String,code:String,callback:Callback){
            PhoneLoginRequest()
                    .number(number)
                    .code(code)
                    .listen(callback)
        }
    }
}