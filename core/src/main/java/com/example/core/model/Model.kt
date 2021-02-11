package com.example.core.model

import org.litepal.crud.LitePalSupport

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/30 下午4:02
 * Describe:所有网络通讯数据模型实体类的基类
 */
abstract class Model :LitePalSupport(){

    //获取当前实体类的实体数据id。比如User类就获取userId,Comment类句获取commentId
    abstract val modelId:Long
}