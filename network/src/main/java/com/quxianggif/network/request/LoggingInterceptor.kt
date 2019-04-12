package com.quxianggif.network.request

import com.example.core.extension.logVerbose
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/12 下午3:17
 * Describe:Okttp网络请求日志拦截器，通过日志记录OkHttp所有请求以及响应的细节
 */
internal class LoggingInterceptor :Interceptor{

    companion object {
        val TAG="LoggingInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request=chain.request()
        val t1=System.nanoTime()
        logVerbose(TAG,"Sending request: "+request.url()+"\n"+request.headers())

        val response=chain.proceed(request)

        val t2=System.nanoTime()

        logVerbose(TAG,"Received response for "+response.request().url()+" in "+(t2-t1)/1e6+"ms\n"+response.headers())

        return response
    }
}