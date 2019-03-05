package com.muzi.repairtime.activity;

import android.content.Intent;
import android.view.View;

import com.muzi.repairtime.Constans;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.data.DataProxy;
import com.muzi.repairtime.databinding.ActivityLoginBinding;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.LoginApi;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.utils.StringUtils;
import com.muzi.repairtime.utils.ToastUtils;
import com.muzi.repairtime.utils.ViewUtils;

/**
 * 登录activity
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        String phone = DataProxy.getInstance().getString(Constans.KEY_PHONE);
        String psd = DataProxy.getInstance().getString(Constans.KEY_PSD);
        if (StringUtils.isNotEmpty(phone) &&
                StringUtils.isNotEmpty(psd)) {
            ViewUtils.setText(getBinding().etPhone, phone);
            ViewUtils.setText(getBinding().etPsd, psd);
            getBinding().cbPsd.setChecked(true);
        } else {
            getBinding().cbPsd.setChecked(false);
        }
        getBinding().setClick(new ClickHandler());
    }

    public class ClickHandler {
        /**
         * 登录
         *
         * @param view
         */
        public void login(View view) {
            final String phone = ViewUtils.getText(getBinding().etPhone);
            if (StringUtils.isEmpty(phone)) {
                ToastUtils.showToast("请输入手机号");
                return;
            }
            final String psd = ViewUtils.getText(getBinding().etPsd);
            if (StringUtils.isEmpty(psd)) {
                ToastUtils.showToast("请输入密码");
            }
            RxHttp.getApi(LoginApi.class)
                    .login(phone, psd)
                    .compose(RxUtils.<BaseEntity>scheduling())
                    .subscribe(new EntityObserver<BaseEntity>(LoginActivity.this) {
                        @Override
                        public void onSuccess() {
                            if (getBinding().cbPsd.isChecked()) {
                                DataProxy.getInstance().set(Constans.KEY_PHONE, phone);
                                DataProxy.getInstance().set(Constans.KEY_PSD, psd);
                            } else {
                                DataProxy.getInstance().remove(Constans.KEY_PHONE, Constans.KEY_PSD);
                            }
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    });
        }

        /**
         * 注册
         *
         * @param view
         */
        public void register(View view) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }

}
