package com.example.main.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.SoundEffectConstants
import android.view.View
import android.widget.Checkable
import android.widget.ImageButton

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/5/5 上午10:46
 * Describe:An extension to [ImageButton] which implements the [Checkable] interface.
 */
class CheckableImageButton(context: Context,attrs:AttributeSet ) :ImageButton(context,attrs),Checkable{

    private var isChecked=false

    override fun isChecked(): Boolean {
        return isChecked
    }

    override fun toggle() {
        setChecked(!isChecked)
    }

    override fun setChecked(checked: Boolean) {
        if(isChecked!=checked){
            isChecked=checked
            refreshDrawableState()
        }
    }

    override fun performClick(): Boolean {
        toggle()
        val handled=super.performClick()
        if(!handled){
            playSoundEffect(SoundEffectConstants.CLICK)
        }
        return handled
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState=super.onCreateDrawableState(extraSpace+1)
        if(isChecked()){
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    companion object {

        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }

}