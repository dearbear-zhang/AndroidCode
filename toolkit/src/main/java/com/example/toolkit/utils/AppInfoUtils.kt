package com.example.toolkit.utils

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.example.toolkit.bean.AppInfoBean
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*

object AppInfoUtils {

    const val userAppFlag = "user"
    const val systemAppFlag = "system"

    fun getAppInfo(packageManager: PackageManager, packageInfo: PackageInfo): AppInfoBean {
        val item = AppInfoBean()
        item.name = packageInfo.applicationInfo.loadLabel(packageManager).toString()
        item.icon = getBitmapFromDrawable(packageInfo.applicationInfo.loadIcon(packageManager))
        item.type = if (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) userAppFlag else systemAppFlag
        item.packageName = packageInfo.packageName
        item.version = packageInfo.versionName ?: ""
        item.versionCode = packageInfo.versionCode
        item.path = packageInfo.applicationInfo.publicSourceDir
        val lastModifiedDate = Date(File(item.path).lastModified())
        item.date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(lastModifiedDate)
        item.size = FileInputStream(item.path).channel.size().toString()
        val permissions = StringBuilder()
        try {
            val sharedPkgList = packageInfo.requestedPermissions
            if (sharedPkgList != null) {
                for (permission in sharedPkgList) {
                    permissions.append(permission).append(",")
                }
            }
            item.permission = permissions.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return item
    }

    private fun getBitmapFromDrawable(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}