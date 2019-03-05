package com.muzi.repairtime.activity;

import android.view.View;

import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.databinding.ActivityRegisterBinding;

/**
 * 注册activity
 */
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        getBinding().setClick(new ClickHandler());
    }

    public class ClickHandler {

        /**
         * 直接登录
         *
         * @param view
         */
        public void login(View view) {
            finish();
        }

        /**
         * 注册
         *
         * @param view
         */
        public void register(View view) {

        }

    }

}
