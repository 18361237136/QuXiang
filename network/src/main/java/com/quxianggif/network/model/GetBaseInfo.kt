package com.quxianggif.network.model

import com.google.gson.annotations.SerializedName
import com.quxianggif.network.request.GetBaseInfoRequest

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/24 下午3:21
 * Describe:获取当前登录用户基本信息请求的实体类封装
 */
class GetBaseInfo :Response(){

    //当前登录用户的昵称
    var nickname:String=""

    //当前登录用户的头像
    var avatar:String=""

    /**
     * 当前登录用户的个人简介。
     */
    var description: String = ""

    //当前登录用户个人主页的背景图。
    @SerializedName("bg_image")
    var bgImage:String=""

    companion object {
        fun getResponse(callback: Callback){
            GetBaseInfoRequest().listen(callback)
        }
    }
}