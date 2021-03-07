package com.example.toolkit.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.toolkit.R
import com.example.toolkit.databinding.ActivityPhoneInfoBinding
import java.util.*

/**
 * Create by dearbear
 * on 2021/3/7
 */
class PhoneInfoActivity : AppCompatActivity() {
    private val TAG = PhoneInfoActivity::class.java.name
    private val mTextBtn: Button? = null
    private val mHandler = Handler()
    private var mItemAdapter: ItemAdapter? = null
    private var mRecyclerView: RecyclerView? = null
    var mBinding: ActivityPhoneInfoBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPhoneInfoBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)
        initView()
        initData()
    }

    private fun initView() {
        mRecyclerView = mBinding?.mainRv
    }

    private fun initData() {
        val data: MutableList<String> = ArrayList()
        data.add("基本信息")
        data.add("应用信息")
        data.add("图片,音频")
        data.add("vvn")

        mItemAdapter = ItemAdapter(data)
        mItemAdapter?.setOnItemClickListener { adapter, view, position ->
            when (position) {
                1 -> startActivity(Intent(this, AppInfoActivity::class.java))
                3 -> startActivity(Intent(this, VvnActivity::class.java))
            }
        }
        mRecyclerView?.adapter = mItemAdapter
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
    }

    internal class ItemAdapter(data: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.common_item_title_click, data) {
        override fun convert(baseViewHolder: BaseViewHolder, s: String) {
            baseViewHolder.setText(R.id.commonItemTitleTv, s)
        }
    }
}