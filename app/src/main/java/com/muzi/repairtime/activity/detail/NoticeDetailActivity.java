package com.muzi.repairtime.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;

import com.muzi.repairtime.BR;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.databinding.ActivityNoticeDetailBinding;

/**
 * 作者: lipeng
 * 时间: 2019/3/25
 * 邮箱: lipeng@moyi365.com
 * 功能: 公告详情
 */
public class NoticeDetailActivity extends BaseActivity<ActivityNoticeDetailBinding, NoticeDetailViewModel> {

    public static void startActivity(Context context, String title, String content) {
        Intent intent = new Intent(context, NoticeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("content", content);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_notice_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initView() {
        super.initView();
        WebSettings settings = binding.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        if (binding.webView.isHardwareAccelerated()) {
            binding.webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
    }

}
