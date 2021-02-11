package com.example.main.feeds.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import org.litepal.LitePal
import org.litepal.crud.LitePalSupport
import java.util.*

/**
 * Anthor: Zhuangmingzhu
 * Date: 2019/6/12 下午5:02
 * Describe:草稿箱的实体类。
 */
@Parcelize
class Draft(val gitPath:String,val content:String,val time: Date) :LitePalSupport(),Parcelable{
    @IgnoredOnParcel
    var id:Long=0
}