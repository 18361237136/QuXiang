package com.example.core.extension

import android.text.TextUtils

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/7/19 下午3:36
 * Describe:字符串操作的扩展工具类。
 */

fun String.getNumbersFromString() = if(TextUtils.isEmpty(this)){
    ""
}else{
    replace("[^0-9]".toRegex(), "")
}