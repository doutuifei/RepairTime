package com.muzi.repairtime.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.imagepicker.util.Utils;
import com.lzy.imagepicker.view.ViewPagerFixed;
import com.muzi.repairtime.R;
import com.muzi.repairtime.adapter.ImagePageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/4/24
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ImagePreviewActivity extends AppCompatActivity {

    private int index;
    private ArrayList<String> imageList;

    protected View content;
    protected View topBar;
    protected ViewPagerFixed mViewPager;
    protected com.muzi.repairtime.adapter.ImagePageAdapter mAdapter;
    protected TextView mTitleCount;

    public static void startIntent(Context context, List<String> imageList, int index) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.putStringArrayListExtra("list", new ArrayList<>(imageList));
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            index = intent.getIntExtra("index", 0);
            imageList = intent.getStringArrayListExtra("list");
        }
    }

    private void initView() {
        //初始化控件
        content = findViewById(R.id.content);

        //因为状态栏透明后，布局整体会上移，所以给头部加上状态栏的margin值，保证头部不会被覆盖
        topBar = findViewById(R.id.top_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) topBar.getLayoutParams();
            params.topMargin = Utils.getStatusHeight(this);
            topBar.setLayoutParams(params);
        }
        topBar.findViewById(R.id.btn_ok).setVisibility(View.GONE);
        topBar.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewPager = findViewById(R.id.viewpager);
        mTitleCount = (TextView) findViewById(R.id.tv_des);

        mAdapter = new ImagePageAdapter(this, imageList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(index, false);

        //初始化当前页面的状态
        mTitleCount.setText(getString(R.string.ip_preview_image_count, index + 1, imageList.size()));

    }

}
