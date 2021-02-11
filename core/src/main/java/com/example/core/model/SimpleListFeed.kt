package com.example.core.model

import com.google.gson.annotations.SerializedName

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/13 下午4:48
 * Describe:SimpleListFeed的实体类，用于存储单列列表展示的Feed数据。
 */
abstract class SimpleListFeed :BaseFeed(){

    @SerializedName("feed_type")
    var feedType=0

    abstract fun refFeed():BaseFeed?
}