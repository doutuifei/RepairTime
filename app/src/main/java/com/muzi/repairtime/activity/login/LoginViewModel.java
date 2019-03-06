package com.muzi.repairtime.activity.login;

import android.app.Application;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.muzi.repairtime.App;
import com.muzi.repairtime.Constans;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.activity.main.MainActivity;
import com.muzi.repairtime.activity.register.RegisterActivity;
import com.muzi.repairtime.data.DataProxy;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.LoginApi;
import com.muzi.repairtime.manager.AppManager;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.utils.StringUtils;
import com.muzi.repairtime.utils.ToastUtils;

/**
 * 作者: lipeng
 * 时间: 2019/3/5
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class LoginViewModel extends BaseViewModel {

    //手机号
    public ObservableField<String> phone = new ObservableField<>("");

    //密码
    public ObservableField<String> password = new ObservableField<>("");

    //是否记住密码
    public ObservableBoolean rememb = new ObservableBoolean(false);

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String phone = DataProxy.getInstance().getString(Constans.KEY_PHONE);
        String password = DataProxy.getInstance().getString(Constans.KEY_PSD);
        this.rememb.set(StringUtils.isNotEmpty(phone) &&
                StringUtils.isNotEmpty(password));
        this.phone.set(phone);
        this.password.set(password);
    }

    /**
     * 登录
     *
     * @param view
     */
    public void login(View view) {
        if (TextUtils.isEmpty(phone.get())) {
            ToastUtils.showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showToast("请输入密码");
            return;
        }
        RxHttp.getApi(LoginApi.class)
                .login(phone.get(), password.get())
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(RxUtils.<BaseEntity>bindToLifecycle(getLifecycleProvider()))
                .subscribe(new EntityObserver<BaseEntity>() {
                    @Override
                    public void onSuccess() {
                        if (rememb.get()) {
                            DataProxy.getInstance().set(Constans.KEY_PHONE, phone.get());
                            DataProxy.getInstance().set(Constans.KEY_PSD, password.get());
                        }else {
                            DataProxy.getInstance().remove(Constans.KEY_PHONE, Constans.KEY_PSD);
                        }
                        App.getInstance().startActivity(new Intent(App.getInstance(), MainActivity.class));
                        AppManager.getAppManager().finishActivity();
                    }
                });
    }

    /**
     * 注册
     *
     * @param view
     */
    public void register(View view) {
        App.getInstance().startActivity(new Intent(App.getInstance(), RegisterActivity.class));
    }

}
