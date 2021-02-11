package com.example.core.model

import com.google.gson.annotations.SerializedName

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 上午11:33
 * Describe:
 */
class HotFeed :WaterFallFeed(){

    @SerializedName("comments_count")
    var commentsCount:Int=0
}