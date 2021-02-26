package com.example.toolkit.adapter

import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.toolkit.R
import com.example.toolkit.bean.AppInfoBean

class AppInfoAdapter(layoutResId: Int, data: MutableList<AppInfoBean>?) : BaseQuickAdapter<AppInfoBean, BaseViewHolder>(layoutResId, data) {

    init {
        addChildClickViewIds(R.id.cb_app_info_export)
    }

    override fun convert(holder: BaseViewHolder, item: AppInfoBean) {
        holder.setText(R.id.tv_app_info_name, item.name)
        holder.setImageBitmap(R.id.iv_app_info_icon, item.icon)
        val cbExport = holder.getView<CheckBox>(R.id.cb_app_info_export)
        cbExport.isChecked = item.isChecked
    }
}