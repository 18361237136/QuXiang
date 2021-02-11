package com.example.main.util.glide

import android.text.TextUtils
import com.example.bumptech.glide.load.model.GlideUrl
import com.example.core.extension.logDebug

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午2:41
 * Describe:用于加载带后缀参数的图片。使用CustomUrl可以使用Glide忽略掉URL地址后面的可变部分，只使用前面固定部分的URL地址来进行缓存。
 */
class CustomUrl(private val gifUrl:String):GlideUrl(gifUrl) {

    override fun getCacheKey(): String {
        if(!TextUtils.isEmpty(gifUrl)){
            val index=gifUrl.indexOf("?")
            val cacheKey=if(index>0){
                gifUrl.substring(0,index)
            }else{
                gifUrl
            }
            logDebug(TAG, "gifUrl: $gifUrl , cache key is $cacheKey")
            return cacheKey
        }
        return ""
    }

    companion object {
        private const val TAG = "CustomUrl"
    }
}