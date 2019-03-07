package com.muzi.repairtime.activity.main.administrator;

import android.os.Bundle;

import com.android.databinding.library.baseAdapters.BR;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.databinding.ActivityAdministratorBinding;

/**
 * 作者: lipeng
 * 时间: 2019/3/7
 * 邮箱: lipeng@moyi365.com
 * 功能: 管理员
 */
public class AdministratorActivity extends BaseActivity<ActivityAdministratorBinding, AdministratorModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_administrator;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

}
