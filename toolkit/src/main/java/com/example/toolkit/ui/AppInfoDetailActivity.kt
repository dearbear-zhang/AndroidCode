package com.example.toolkit.ui

import android.content.pm.PackageInfo
import android.os.Bundle
import android.text.format.Formatter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.toolkit.R
import com.example.toolkit.utils.AppInfoUtils

class AppInfoDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info_detail)
        val ivIcon = findViewById<ImageView>(R.id.iv_app_info_detail_icon)
        val tvName = findViewById<TextView>(R.id.tv_app_info_detail_name)
        val tvPackageName = findViewById<TextView>(R.id.tv_app_info_detail_package_name)
        val tvVersion = findViewById<TextView>(R.id.tv_app_info_detail_version)
        val tvVersionCode = findViewById<TextView>(R.id.tv_app_info_detail_version_code)
        val tvType = findViewById<TextView>(R.id.tv_app_info_detail_type)
        val tvLastModifiedDate = findViewById<TextView>(R.id.tv_app_info_detail_last_modified_date)
        val tvSize = findViewById<TextView>(R.id.tv_app_info_detail_size)
        val tvPath = findViewById<TextView>(R.id.tv_app_info_detail_path)
        val tvPermissions = findViewById<TextView>(R.id.tv_app_info_detail_permission)
        val packageInfo:PackageInfo = intent.getParcelableExtra("packageInfo")
        val item = AppInfoUtils.getAppInfo(this.packageManager, packageInfo)
        ivIcon.setImageBitmap(item.icon)
        tvName.text = String.format(getString(R.string.app_info_name), item.name)
        tvPackageName.text = String.format(getString(R.string.app_info_package_name), item.packageName)
        tvVersion.text = String.format(getString(R.string.app_info_version), item.version)
        tvVersionCode.text = String.format(getString(R.string.app_info_version_code), java.lang.String.valueOf(item.versionCode))
        tvType.text = String.format(getString(R.string.app_info_type), item.type)
        tvLastModifiedDate.text = String.format(getString(R.string.app_info_last_modified_date), item.date)
        tvSize.text = String.format(getString(R.string.app_info_size), formatFileSize(item.size))
        tvPath.text = String.format(getString(R.string.app_info_path), item.path)
        tvPermissions.text = String.format(getString(R.string.app_info_permission), item.permission.replace(",", "\n"))
    }

    private fun formatFileSize(size: String): String {
        return Formatter.formatFileSize(this, size.toLong())
    }
}