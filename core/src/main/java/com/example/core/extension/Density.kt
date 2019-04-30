package com.example.core.extension

import com.example.core.GifFun

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/30 下午3:30
 * Describe:单位转换工具类，会根据手机的分辨率来进行单位转换。
 */
fun dp2px(dp:Float):Int{
    val scale=GifFun.getContext().resources.displayMetrics.density
    return (dp*scale+0.5f).toInt()
}

fun px2dp(px:Float):Int{
    val scale=GifFun.getContext().resources.displayMetrics.density
    return (px/scale+0.5f).toInt()
}