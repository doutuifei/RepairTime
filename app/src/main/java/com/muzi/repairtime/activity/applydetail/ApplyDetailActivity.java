package com.muzi.repairtime.activity.applydetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.muzi.repairtime.BR;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.databinding.ActivityApplyDetailBinding;
import com.muzi.repairtime.entity.RepairEntity;

public class ApplyDetailActivity extends BaseActivity<ActivityApplyDetailBinding, ApplyDetailViewModel> {

    public static void startActivity(Context context, RepairEntity.PagesBean.ListBean listBean) {
        Intent intent = new Intent(context, ApplyDetailActivity.class);
        intent.putExtra("bean", listBean);
        context.startActivity(intent);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_apply_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initView() {
        super.initView();
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
