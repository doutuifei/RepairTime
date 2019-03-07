package com.muzi.repairtime.activity.main.maintenance;

import android.os.Bundle;

import com.muzi.repairtime.BR;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.databinding.ActivityMaintenanceBinding;

/**
 * 维修员
 */
public class MaintenanceActivity extends BaseActivity<ActivityMaintenanceBinding,MaintenanceViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_maintenance;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
