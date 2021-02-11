package com.example.main.util

import com.example.core.GifFun
import com.example.core.extension.logWarn
import com.example.core.extension.showToastOnUiThread
import com.example.core.util.GlobalUtil
import com.example.main.R
import com.example.main.event.ForceToLoginEvent
import com.quxianggif.network.exception.ResponseCodeException
import com.quxianggif.network.model.Response
import org.greenrobot.eventbus.EventBus
import java.lang.Exception
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/4/15 下午7:20
 * Describe:对服务器的返回进行相应的逻辑处理。注意此类只处理公众的返回逻辑，射击具体的业务逻辑，仍然交由接口调用处自行处理
 */
object ResponseHandler {

    private val TAG = "ResponseHandler"

    //当网络请求正常相应的时候，根据状态码处理通用部分的逻辑
    fun handleResponse(response: Response?):Boolean{
        if(response==null){
            logWarn(TAG,"handleResponse: response is null")
            showToastOnUiThread(GlobalUtil.getString(R.string.unknown_error))
            return true
        }
        val status=response.status
        when(status){
            10001, 10002, 10003->{
                logWarn(TAG, "handleResponse: status code is $status")
                GifFun.logout()
                showToastOnUiThread(GlobalUtil.getString(R.string.login_status_expired))
                val event=ForceToLoginEvent()
                EventBus.getDefault().post(event)
                return true
            }
            19000->{
                logWarn(TAG, "handleResponse: status code is 19000")
                showToastOnUiThread(GlobalUtil.getString(R.string.unknown_error))
                return true
            }
            else->return false
        }
    }

    //当网络请求没有正常响应的时候，根据异常类型进行相应的处理
    fun handleFailure(e:Exception){
        when(e){
            is ConnectException -> showToastOnUiThread(GlobalUtil.getString(R.string.network_connect_error))
            is SocketTimeoutException -> showToastOnUiThread(GlobalUtil.getString(R.string.network_connect_timeout))
            is ResponseCodeException -> showToastOnUiThread(GlobalUtil.getString(R.string.network_response_code_error) + e.responseCode)
            is NoRouteToHostException -> showToastOnUiThread(GlobalUtil.getString(R.string.no_route_to_host))
            else -> {
                logWarn(TAG, "handleFailure exception is $e")
                showToastOnUiThread(GlobalUtil.getString(R.string.unknown_error))
            }
        }
    }
}