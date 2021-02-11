package com.example.core.extension

import com.example.core.GifFun
import com.example.core.model.Model
import kotlin.concurrent.thread

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午4:01
 * Describe:
 */
/**
 * 查询Model并回调集合中第一条符合给定参数条件元素的下标，如未查到则回调-1。
 */
fun <T : Model> findModelIndex(models: List<T>?, modelId: Long, action: (index: Int) -> Unit) {
    thread {
        var index = -1
        if (models != null && !models.isEmpty()) {
            for (i in models.indices) {
                val model = models[i]
                if (model.modelId == modelId) {
                    index = i
                    break
                }
            }
        }
        GifFun.getHandler().post {
            action(index)
        }
    }
}

/**
 * 查询Model并回调集合中第一条符合给定参数条件元素的下标，如未查到则不进行回调。
 */
fun <T : Model> searchModelIndex(models: List<T>?, modelId: Long, action: (index: Int) -> Unit) {
    thread {
        var index = -1
        if (models != null && !models.isEmpty()) {
            for (i in models.indices) {
                val model = models[i]
                if (model.modelId == modelId) {
                    index = i
                    break
                }
            }
        }
        if (index != -1) {
            GifFun.getHandler().post {
                action(index)
            }
        }
    }
}

