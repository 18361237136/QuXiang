package com.example.core.extension

import android.os.Handler
import android.view.View

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/30 下午2:38
 * Describe:Handler的扩展类
 */
inline fun Handler.postDelayed(delayMillis:Long,crossinline action:()->Unit):Runnable{
    val runnable= Runnable { action() }
    postDelayed(runnable,delayMillis)
    return runnable
}

inline fun View.postDelayed(delayMillis: Long,crossinline action:()->Unit):Runnable{
    val runnable= Runnable { action() }
    postDelayed(runnable,delayMillis)
    return runnable
}