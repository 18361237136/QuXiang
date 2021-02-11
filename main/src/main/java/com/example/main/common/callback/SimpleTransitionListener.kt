package com.example.main.common.callback

import android.annotation.TargetApi
import android.os.Build
import android.transition.Transition

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/23 下午7:48
 * Describe:为了监听动画时不用实现那么多方法
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
open class SimpleTransitionListener:Transition.TransitionListener {
    override fun onTransitionEnd(transition: Transition?) {
    }

    override fun onTransitionResume(transition: Transition?) {
    }

    override fun onTransitionPause(transition: Transition?) {
    }

    override fun onTransitionCancel(transition: Transition?) {
    }

    override fun onTransitionStart(transition: Transition?) {
    }

}