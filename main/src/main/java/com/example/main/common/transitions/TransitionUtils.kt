package com.example.main.common.transitions

import android.annotation.TargetApi
import android.os.Build
import android.support.annotation.IdRes
import android.transition.Transition
import android.transition.TransitionSet
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/11 下午6:54
 * Describe:动画工具类
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
object TransitionUtils {

    fun findTransition(set: TransitionSet,clazz:Class<out Transition>):Transition?{
        for(i in 0 until set.transitionCount){
            val transition=set.getTransitionAt(i)
            if(transition.javaClass==clazz){
                return transition
            }
            if(transition is TransitionSet){
                val child= findTransition(transition,clazz)
                if(child!=null) return child
            }
        }
        return null
    }

    fun findTransition(
            set: TransitionSet,
            clazz: Class<out Transition>,
            @IdRes targetId: Int): Transition? {
        for (i in 0 until set.transitionCount) {
            val transition = set.getTransitionAt(i)
            if (transition.javaClass == clazz) {
                if (transition.targetIds.contains(targetId)) {
                    return transition
                }
            }
            if (transition is TransitionSet) {
                val child = findTransition(transition, clazz, targetId)
                if (child != null) return child
            }
        }
        return null
    }

    fun setAncestralClipping(view: View, clipChildren: Boolean): List<Boolean> {
        return setAncestralClipping(view, clipChildren, ArrayList())
    }

    private fun setAncestralClipping(
            view: View, clipChildren: Boolean, was: MutableList<Boolean>): List<Boolean> {
        if (view is ViewGroup) {
            was.add(view.clipChildren)
            view.clipChildren = clipChildren
        }
        val parent = view.parent
        if (parent != null && parent is ViewGroup) {
            setAncestralClipping(parent, clipChildren, was)
        }
        return was
    }

    fun restoreAncestralClipping(view: View, was: MutableList<Boolean>) {
        if (view is ViewGroup) {
            view.clipChildren = was.removeAt(0)
        }
        val parent = view.parent
        if (parent != null && parent is ViewGroup) {
            restoreAncestralClipping(parent, was)
        }
    }

    open class TransitionListenerAdapter : Transition.TransitionListener {

        override fun onTransitionStart(transition: Transition) {}

        override fun onTransitionEnd(transition: Transition) {}

        override fun onTransitionCancel(transition: Transition) {}

        override fun onTransitionPause(transition: Transition) {}

        override fun onTransitionResume(transition: Transition) {}
    }

}