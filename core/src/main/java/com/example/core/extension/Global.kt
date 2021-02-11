package com.example.core.extension

import android.annotation.SuppressLint
import android.os.Looper
import android.widget.Toast
import com.example.core.GifFun

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/15 下午7:24
 * Describe:弹出Toast扩展工具
 */

private var toast:Toast?=null

//弹出Toast信息，如果不是在主线程中就调用此方法，Toast信息将不会显示
@SuppressLint("ShowToast")
@JvmOverloads
fun showToast(content:String,duration:Int= Toast.LENGTH_SHORT){
    if(Looper.myLooper()== Looper.getMainLooper()){
        if(toast==null){
            toast= Toast.makeText(GifFun.getContext(),content,duration)
        }else{
            toast?.setText(content)
        }
        toast?.show()
    }
}

//切换到主线程后弹出Toast信息，此方法不管是主线程还是子线程，都可以成功弹出Toast信息
@SuppressLint("ShowToast")
@JvmOverloads
fun showToastOnUiThread(content:String,duration:Int= Toast.LENGTH_SHORT){
    GifFun.getHandler().post {
        if(toast==null){
            toast= Toast.makeText(GifFun.getContext(),content,duration)
        }else{
            toast?.setText(content)
        }
        toast?.show()
    }
}