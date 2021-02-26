package com.example.toolkit.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.toolkit.R
import com.example.toolkit.adapter.AppInfoAdapter
import com.example.toolkit.bean.AppInfoBean
import com.example.toolkit.utils.AppInfoUtils
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class AppInfoActivity : AppCompatActivity() {

    private var mContext = this
    private lateinit var mAdapter: AppInfoAdapter
    private var mData = ArrayList<AppInfoBean>()
    private var mAllInfoList = ArrayList<AppInfoBean>()
    private lateinit var mProgressDialog: MaterialDialog
    private lateinit var mCbDisplaySystemApp: CheckBox
    private lateinit var mEtSearch: EditText

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info)
        val rvAppInfo = findViewById<RecyclerView>(R.id.rv_app_info_list)
        rvAppInfo.layoutManager = GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)
        mAdapter = AppInfoAdapter(R.layout.item_app_info, mData)
        mAdapter.setOnItemClickListener { _: BaseQuickAdapter<*, *>?, _: View?, position: Int ->
            val intent = Intent(mContext, AppInfoDetailActivity::class.java)
            val packageInfo = mContext.packageManager.getPackageInfo(mData[position].packageName, PackageManager.GET_PERMISSIONS)
            intent.putExtra("packageInfo", packageInfo)
            startActivity(intent)
        }
        mAdapter.setOnItemChildClickListener { _: BaseQuickAdapter<*, *>?, view: View, position: Int ->
            if (view.id == R.id.cb_app_info_export) {
                val item = mData[position]
                item.isChecked = !item.isChecked
                mAdapter.notifyItemChanged(position)
            }
        }
        rvAppInfo.adapter = mAdapter
        val btnExport = findViewById<Button>(R.id.btn_app_info_export)
        btnExport.setOnClickListener {
            val exportList = ArrayList<AppInfoBean>()
            for (item in mAllInfoList) {
                if (item.isChecked) {
                    exportList.add(item)
                }
            }
            if (exportList.isEmpty()) {
                Toast.makeText(mContext, "请至少选择一个应用导出！", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: 2021/2/3 export
                for (item in exportList) {
                    Log.i("xxx", "onClick: export: " + item.name)
                }
            }
        }
        mCbDisplaySystemApp = findViewById(R.id.cb_app_info_display_system_app)
        mCbDisplaySystemApp.setOnCheckedChangeListener { _: CompoundButton?, _: Boolean -> initData() }
        mEtSearch = findViewById(R.id.et_app_info_search)
        RxTextView.textChanges(mEtSearch)
                .skip(1)
                .debounce(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { search() }
        initProgressDialog()
        initData()
    }

    private fun initProgressDialog() {
        val builder = MaterialDialog.Builder(this)
        builder.progress(true, 100)
        builder.title(R.string.hint)
        builder.content(R.string.loading_hint)
        builder.canceledOnTouchOutside(false)
        builder.cancelable(false)
        mProgressDialog = builder.build()
    }

    private fun initData() {
        GlobalScope.launch(Dispatchers.Main) {
            mProgressDialog.show()
            withContext(Dispatchers.Default) {
                mData.clear()
                if (mAllInfoList.isEmpty()) {
                    val packageManager = mContext.packageManager
                    val packageInfoList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS)
                    for (info in packageInfoList) {
                        val item = AppInfoUtils.getAppInfo(packageManager, info)
                        mAllInfoList.add(item)
                    }
                }
                if (mCbDisplaySystemApp.isChecked) {
                    mData.addAll(mAllInfoList)
                } else {
                    for (item in mAllInfoList) {
                        if (AppInfoUtils.userAppFlag == item.type) {
                            mData.add(item)
                        }
                    }
                }
            }
            mAdapter.notifyDataSetChanged()
            if (mProgressDialog.isShowing) {
                mProgressDialog.dismiss()
            }
        }
    }

    private fun search() {
        if (TextUtils.isEmpty(mEtSearch.text)) {
            initData()
            return
        }
        GlobalScope.launch(Dispatchers.Main) {
            mProgressDialog.show()
            searchData()
            mAdapter.notifyDataSetChanged()
            if (mProgressDialog.isShowing) {
                mProgressDialog.dismiss()
            }
        }
    }

    suspend fun searchData() {
        withContext(Dispatchers.Default) {
            mData.clear()
            for (item in mAllInfoList) {
                if (!mCbDisplaySystemApp.isChecked && AppInfoUtils.systemAppFlag == item.type) {
                    continue
                }
                if (item.name.toLowerCase(Locale.getDefault()).contains(mEtSearch.text.toString().trim().toLowerCase(Locale.getDefault()))) {
                    mData.add(item)
                }
            }
        }
    }
}