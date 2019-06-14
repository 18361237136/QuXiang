package com.example.core.model

import com.google.gson.annotations.SerializedName

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/14 上午10:34
 * Describe:UserFeed的实体类，用于存储服务器返回的用户个人主页列表的Feed数据。
 */
class UserFeed :SimpleListFeed(){

    @SerializedName("ref_feed")
    var refFeed:RefFeed?=null

    override fun refFeed(): RefFeed? {
        return refFeed
    }
}