package com.example.toolkit.bean

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppInfoBean(var name: String = "",
                       var icon: Bitmap? = null,
                       var packageName: String = "",
                       var version: String = "",
                       var versionCode: Int = 0,
                       var type: String = "",
                       var date: String = "",
                       var permission: String = "",
                       var size: String = "",
                       var path: String = "",
                       var serial: String = "",
                       var apkMd5: String = "",
                       var certMd5: String = "",
                       var issuerDn: String = "",
                       var isDelete: String = "") : Parcelable {
    @IgnoredOnParcel
    var isChecked: Boolean = false
}