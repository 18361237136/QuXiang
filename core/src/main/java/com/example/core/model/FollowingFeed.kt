package com.example.core.model

import com.google.gson.annotations.SerializedName

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/13 下午4:47
 * Describe:FollowingFeed的实体类，用于存储服务器返回的用户关注列表的Feed数据
 */
class FollowingFeed :SimpleListFeed(){

    @SerializedName("ref_feed")
    var refFeed:RefFeed?=null

    override fun refFeed(): BaseFeed? {
        return refFeed
    }
}