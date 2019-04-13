package com.muzi.repairtime.activity.login;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.muzi.repairtime.Constans;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.activity.main.MainActivity;
import com.muzi.repairtime.activity.register.RegisterActivity;
import com.muzi.repairtime.command.BindingCommand;
import com.muzi.repairtime.command.BindingConsumerAction;
import com.muzi.repairtime.data.DataProxy;
import com.muzi.repairtime.entity.LoginEntity;
import com.muzi.repairtime.event.EventConstan;
import com.muzi.repairtime.event.LiveEventBus;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.LoginApi;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.utils.StringUtils;
import com.muzi.repairtime.utils.ToastUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

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

    /**
     * 点击登录
     */
    public BindingCommand<View> loginClickCommand = new BindingCommand<>(new BindingConsumerAction<View>() {
        @Override
        public void call(View view) {
            login();
        }
    });

    /**
     * 注册
     */
    public BindingCommand<View> registerClickCommand = new BindingCommand<View>(new BindingConsumerAction<View>() {
        @Override
        public void call(View view) {
            startActivity(RegisterActivity.class);
        }
    });

    @Override
    public void initData() {
        super.initData();
        String phone = DataProxy.getInstance().getString(Constans.KEY_PHONE);
        String password = DataProxy.getInstance().getString(Constans.KEY_PSD);
        this.rememb.set(StringUtils.isNotEmpty(phone) &&
                StringUtils.isNotEmpty(password));
        this.phone.set(phone);
        this.password.set(password);

        LiveEventBus.get().with(EventConstan.ACCOUNT, String[].class)
                .observe(getLifecycleOwner(), new Observer<String[]>() {
                    @Override
                    public void onChanged(@Nullable String[] strings) {
                        LoginViewModel.this.phone.set(strings[0]);
                        LoginViewModel.this.password.set(strings[1]);
                    }
                });
    }

    /**
     * 登录
     */
    public void login() {
        if (TextUtils.isEmpty(phone.get())) {
            ToastUtils.showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showToast("请输入密码");
            return;
        }
        RxHttp.getApi(LoginApi.class)
                .login(phone.get(), password.get(), PushServiceFactory.getCloudPushService().getDeviceId())
                .compose(RxUtils.<LoginEntity>scheduling())
                .compose(RxUtils.<LoginEntity>exceptionTransformer())
                .compose(getLifecycleProvider().<LoginEntity>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new EntityObserver<LoginEntity>(this) {
                    @Override
                    public void onSuccess(LoginEntity entity) {
                        if (rememb.get()) {
                            DataProxy.getInstance().set(Constans.KEY_PHONE, phone.get());
                            DataProxy.getInstance().set(Constans.KEY_PSD, password.get());
                        } else {
                            DataProxy.getInstance().remove(Constans.KEY_PHONE, Constans.KEY_PSD);
                        }
                        LoginEntity.UserBean userBean = entity.getUser();
                        String type = userBean.getType();
                        DataProxy.getInstance().set(Constans.KEY_TYPE, type);
                        DataProxy.getInstance().set(Constans.KEY_USER, userBean.getName());
                        CloudPushService cloudPushService = PushServiceFactory.getCloudPushService();
                        cloudPushService.bindAccount(phone.get(), new CommonCallback() {
                            @Override
                            public void onSuccess(String s) {

                            }

                            @Override
                            public void onFailed(String s, String s1) {

                            }
                        });
                        startActivity(MainActivity.class);
                        finish();
                    }
                });
    }

}
