package com.example.toolkit.ui.slide;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.toolkit.R;
import com.example.toolkit.adapter.ImagetAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by dearbear
 * on 2020/8/1
 * <p>
 * 各种滑动冲突的场景及解决方案
 */
public class ScrollingTestActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ListView mListView;
    private PagerAdapter mPagerAdapter;
    private SimpleAdapter mSimpleAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ScrollView mScrollView;
    private boolean mIsSvToBottom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_test);
        initView();
        initData();
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        mSwipeRefreshLayout = findViewById(R.id.refresh_ly);
        mViewPager = findViewById(R.id.view_pager);
        mScrollView = findViewById(R.id.scroll_view);
        mListView = findViewById(R.id.list_view);
    }

    private void initData() {
        scrollViewInit();
        listViewInit();
        viewPagerInit();
    }

    /***
     * ①解决 ScrollView和SwipeRefreshLayout滑动冲突的问题; ②解决 Scrollview和 ListView滑动冲突的问题
     */
    @SuppressLint("ClickableViewAccessibility")
    private void scrollViewInit() {
        // SwipeRefreshLayout 滑动冲突
        mScrollView.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mSwipeRefreshLayout.setEnabled(false);
                    mSwipeRefreshLayout.requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_UP:
                    mSwipeRefreshLayout.setEnabled(true);
                    mSwipeRefreshLayout.requestDisallowInterceptTouchEvent(false);
                    break;
                default:
                    break;
            }
            return false;
        });
        // 与 listview 滑动冲突 解决方案
        mScrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            // 滑动的距离加上本身的高度与子View的高度对比
            if (scrollY + mScrollView.getHeight() >= mScrollView.getChildAt(0).getMeasuredHeight()) {
                // ScrollView滑动到底部
                mIsSvToBottom = true;
            } else {
                mIsSvToBottom = false;
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(false);
        });
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * listView初始化, 与scrollview 滑动冲突 解决方案
     * A: ScrollView在未滑动到底部时候，如果点击并滑动ListView时候，ListView是不能滑动的，不禁止。
     * B: 如果ScrollView滑动到底部，且ListView已经到顶部，继续下拉ListView，其实会拉动ScrollView，不禁止。
     * C: 如果ScrollView滑动到底部，用户向上滑，ListView滑动，禁止ScrollView截断点击事件能力
     */
    @SuppressLint("ClickableViewAccessibility")
    private void listViewInit() {
        // listview data init
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", "name_" + i);
            data.add(item);
        }
        mSimpleAdapter = new SimpleAdapter(this, data, R.layout.item_name, new String[]{"name"}, new int[]{R.id.name});
        mListView.setAdapter(mSimpleAdapter);
        // 解决 scrollview 滑动冲突
        mListView.setOnTouchListener(new View.OnTouchListener() {
            private float mLastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    mLastY = event.getY();
                }
                if (action == MotionEvent.ACTION_MOVE) {
                    int top = mListView.getChildAt(0).getTop();
                    float nowY = event.getY();
                    float THRESHOLD_Y_LIST_VIEW = 30f;
                    if (!mIsSvToBottom) {
                        // 允许scrollview拦截点击事件, scrollView滑动
                        mScrollView.requestDisallowInterceptTouchEvent(false);
                    } else if (top == 0 && nowY - mLastY > THRESHOLD_Y_LIST_VIEW) {
                        // 允许scrollview拦截点击事件, scrollView滑动
                        mScrollView.requestDisallowInterceptTouchEvent(false);
                    } else {
                        // 不允许scrollview拦截点击事件， listView滑动
                        mScrollView.requestDisallowInterceptTouchEvent(true);
                    }
                }
                return false;
            }
        });
    }

    /**
     * viewPager初始化
     */
    @SuppressLint("ClickableViewAccessibility")
    private void viewPagerInit() {
        List<Integer> data = new ArrayList<>();
        data.add(R.drawable.common_avatar_round);
        data.add(R.drawable.common_bg_header);
        data.add(R.drawable.image_lena);

        mPagerAdapter = new ImagetAdapter(this, data);
        mViewPager.setAdapter(mPagerAdapter);
        // 解决 ViewPager 和 SwipeRefreshLayout 的;;滑动冲突问题
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            private float mLastX, mLastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // 记录点击到ViewPager时候，手指的X坐标
                        mLastX = event.getX();
                        mLastY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (Math.abs(event.getX() - mLastX) > Math.abs(event.getY() - mLastY)) {
                            mSwipeRefreshLayout.setEnabled(false);
                            mSwipeRefreshLayout.requestDisallowInterceptTouchEvent(true);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        mSwipeRefreshLayout.setEnabled(true);
                        mSwipeRefreshLayout.requestDisallowInterceptTouchEvent(false);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}