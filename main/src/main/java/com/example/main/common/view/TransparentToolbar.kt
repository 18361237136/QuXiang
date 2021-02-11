package com.example.main.common.view

import android.content.Context
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.MotionEvent


/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/7/18 下午5:25
 * Describe:用于需要透明化显示的Toolbar，使用这种Toolbar后，即使被Toolbar盖住的区域依然可以点击，点击了被Toolbar盖住的部分
 * 明明可以看到却无法点击的问题
 */

class TransparentToolbar : Toolbar {

    constructor(context:Context):super(context){}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

}