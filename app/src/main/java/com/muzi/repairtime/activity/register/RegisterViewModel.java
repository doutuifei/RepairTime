package com.muzi.repairtime.activity.register;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.command.BindingCommand;
import com.muzi.repairtime.command.BindingConsumerAction;

/**
 * 作者: lipeng
 * 时间: 2019/3/5
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class RegisterViewModel extends BaseViewModel {

    public ObservableField<String> userName = new ObservableField<>("");

    public ObservableField<String> phone = new ObservableField<>("");

    public ObservableField<String> password = new ObservableField<>("");

    public ObservableField<String> confirmPsd = new ObservableField<>("");

    public ObservableField<String> company = new ObservableField<>("");

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 输入手机号
     */
    public BindingCommand<String> phoneCommand = new BindingCommand<>(new BindingConsumerAction<String>() {
        @Override
        public void call(String s) {
            Log.e("RegisterViewModel", s);
        }
    });

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
