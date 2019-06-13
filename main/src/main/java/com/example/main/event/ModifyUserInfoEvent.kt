package com.example.main.event

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午4:00
 * Describe:修改用户信息的事件消息。
 */
class ModifyUserInfoEvent :MessageEvent(){
    var modifyNickname = false

    var modifyDescription = false

    var modifyAvatar = false

    var modifyBgImage = false

}