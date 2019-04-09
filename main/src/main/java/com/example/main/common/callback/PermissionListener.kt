package com.example.main.common.callback

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/8 上午11:44
 * Describe:权限回调接口
 */
interface PermissionListener {

    fun onGranted()

    fun onDenied(deniedPermissions:List<String>)
}