package com.muzi.repairtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.muzi.repairtime.Constans;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.login.LoginActivity;
import com.muzi.repairtime.activity.main.MainActivity;
import com.muzi.repairtime.data.DataProxy;
import com.muzi.repairtime.utils.StringUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String type = DataProxy.getInstance().getString(Constans.KEY_TYPE, "");
        Intent intent = new Intent();
        if (StringUtils.isEmpty(type)) {
            intent.setClass(SplashActivity.this, LoginActivity.class);
        } else {
            intent.setClass(SplashActivity.this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
