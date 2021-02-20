package com.mine.dearbear.view

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dearbear.utilcode.socket.SocketServiceHelper
import com.mine.dearbear.R
import com.mine.dearbear.databinding.ActivityMainBinding
import java.util.*

class MainActivity : Activity() {
    private val TAG = MainActivity::class.java.name
    private val mTextBtn: Button? = null
    private val mHandler = Handler()
    private var mItemAdapter: ItemAdapter? = null
    private var mRecyclerView: RecyclerView? = null
    var mBinding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
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

        mItemAdapter = ItemAdapter(data)
        mRecyclerView?.adapter = mItemAdapter
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        //        mItemAdapter.notifyDataSetChanged();

//        Toast.makeText(this, "集成模式启动", Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(this, ScrollingTestActivity.class);
//        startActivity(intent);
    }

    private fun test() {
        SocketServiceHelper.getInstance().register { s: String? ->
            mHandler.post {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
            }
        }
    }

    internal class ItemAdapter(data: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.common_item_title_click, data) {
        override fun convert(baseViewHolder: BaseViewHolder, s: String) {
            baseViewHolder.setText(R.id.commonItemTitleTv, s)
        }
    }

}