package com.quxianggif.network.model

import com.quxianggif.network.request.PhoneRegisterRequest

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/25 下午12:00
 * Describe:
 */
class PhoneRegister :BaseRegister(){

    companion object{

        fun getResponse(number:String,code:String,nickname:String,callback: Callback){
            PhoneRegisterRequest()
                    .number(number)
                    .code(code)
                    .nickname(nickname)
                    .listen(callback)
        }
    }
}