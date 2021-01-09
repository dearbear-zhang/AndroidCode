package com.mine.dearbear.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import com.dearbear.utilcode.socket.SocketServiceHelper;
import com.example.toolkit.ui.slide.ScrollingTestActivity;
import com.mine.dearbear.R;


public class MainActivity extends Activity {
    private String TAG = MainActivity.class.getName();
    private Button mTextBtn;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        initData();
    }

    private void initView() {
        mTextBtn = findViewById(R.id.test_btn);
        mTextBtn.setOnClickListener(v -> {
            SocketServiceHelper.getInstance().register(s -> {
                mHandler.post(() -> {
                    Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                });
            });
        });
    }

    private void initData() {
        Toast.makeText(this, "集成模式启动", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ScrollingTestActivity.class);
        startActivity(intent);
    }
}
