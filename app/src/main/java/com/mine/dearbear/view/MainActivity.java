package com.mine.dearbear.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.dearbear.utilcode.socket.SocketServiceHelper;
import com.mine.dearbear.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private String TAG = MainActivity.class.getName();
    private Button mTextBtn;
    private Handler mHandler = new Handler();
    private ItemAdapter mItemAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.mainRv);
    }

    private void initData() {
        List<String> data = new ArrayList<>();
        data.add("基本信息");
        data.add("应用信息");
        data.add("图片,音频");
        mItemAdapter = new ItemAdapter(data);
        mRecyclerView.setAdapter(mItemAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mItemAdapter.notifyDataSetChanged();

//        Toast.makeText(this, "集成模式启动", Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(this, ScrollingTestActivity.class);
//        startActivity(intent);
    }

    private void test() {
        SocketServiceHelper.getInstance().register(s -> {
            mHandler.post(() -> {
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            });
        });
    }


    static class ItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ItemAdapter(List<String> data) {
            super(R.layout.common_item_title_click, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
            baseViewHolder.setText(R.id.commonItemTitleTv, s);
        }
    }

}
