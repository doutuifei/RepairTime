package com.muzi.repairtime.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.databinding.ActivityNoticeDetailBinding;
import com.zzhoujay.richtext.RichText;

/**
 * 作者: lipeng
 * 时间: 2019/3/25
 * 邮箱: lipeng@moyi365.com
 * 功能: 公告详情
 */
public class NoticeDetailActivity extends BaseActivity<ActivityNoticeDetailBinding, BaseViewModel> {

    public static void startActivity(Context context, String title, String summary) {
        Intent intent = new Intent(context, NoticeDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("summary", summary);
        context.startActivity(intent);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_notice_detail;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent = getIntent();
        // 通知标题
        String title = intent.getStringExtra("title");
        // 通知内容
        String summary = intent.getStringExtra("summary");

        RichText.initCacheDir(this);
        binding.toolbar.setTitle(title);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RichText.from(summary).into(binding.content);
    }

}
