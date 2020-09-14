package com.mine.dearbear.view.activity;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import android.util.Log;
import android.widget.Toolbar;

import com.mine.dearbear.R;


/**
 * TabLayout 用法示例,参考如下用例
 * https://juejin.im/post/5864eb13570c3500695dcd1a
 */
public class TabLayoutActivity extends Activity {
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private String TAG = TabLayoutActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        initView();
        initData();
    }

    private void initView() {
        mTabLayout = findViewById(R.id.tab_layout2);
    }

    private void initData() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i(TAG, "onTabSelected:" + tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
