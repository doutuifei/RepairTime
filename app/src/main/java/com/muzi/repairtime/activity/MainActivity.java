package com.muzi.repairtime.activity;

import android.os.Bundle;

import com.muzi.repairtime.BR;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity1;
import com.muzi.repairtime.activity.main.MainViewModel;
import com.muzi.repairtime.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity1<ActivityMainBinding, MainViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

}
