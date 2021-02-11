package com.example.core.util

import android.os.Build

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/11 下午2:45
 * Describe:以更加可读的方式提供Android系统版本号的判断方法
 */
object AndroidVersion {

    //判断当前手机系统版本API是否是16以上
    fun hasJellyBean():Boolean{
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN
    }

    //判断当前手机系统版本API是否是17以上
    fun hasJellyBeanMR1():Boolean{
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1
    }

    //判断当前手机系统版本API是否是18以上
    fun hasJellyBeanMR2():Boolean{
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2
    }

    //判断当前手机系统版本API是否是19以上
    fun hasKitkat():Boolean{
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT
    }

    //判断当前手机系统版本API是否是21以上
    fun hasLollipop():Boolean{
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP
    }

    //判断当前手机系统版本API是否是22以上
    fun hasLollipopMR1():Boolean{
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1
    }

    //判断当前手机系统版本API是否是23以上
    fun hasMarshmallow():Boolean{
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.M
    }

    //判断当前手机系统版本API是否是24以上
    fun hasNougat():Boolean{
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.N
    }

}