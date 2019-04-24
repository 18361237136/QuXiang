package com.quxianggif.network.model

import com.quxianggif.network.request.FetchVCodeRequest

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/24 上午10:13
 * Describe:获取短信验证码请求的实体类封装
 */
class FetchVCode:Response() {

    companion object {
        fun getResponse(number:String,callback:Callback){
            FetchVCodeRequest().number(number).listen(callback)
        }
    }
}