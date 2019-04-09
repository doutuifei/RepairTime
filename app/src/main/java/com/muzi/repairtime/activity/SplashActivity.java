package com.muzi.repairtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.muzi.repairtime.Constans;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.login.LoginActivity;
import com.muzi.repairtime.activity.main.AdministratorActivity;
import com.muzi.repairtime.activity.main.EmployeeActivity;
import com.muzi.repairtime.activity.main.MaintenanceActivity;
import com.muzi.repairtime.data.DataProxy;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String type = DataProxy.getInstance().getString(Constans.KEY_TYPE, "");
        Intent intent = new Intent();
        switch (type) {
            case "普通用户":
                intent.setClass(SplashActivity.this, EmployeeActivity.class);
                break;
            case "维修员":
                intent.setClass(SplashActivity.this, MaintenanceActivity.class);
                break;
            case "管理员":
                intent.setClass(SplashActivity.this, AdministratorActivity.class);
                break;
            default:
                intent.setClass(SplashActivity.this, LoginActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}
