package com.muzi.repairtime.fragment.psd;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.command.BindingCommand;
import com.muzi.repairtime.command.BindingConsumerAction;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.UserApi;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.utils.StringUtils;
import com.muzi.repairtime.utils.ToastUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ChangePsdViewModel extends BaseViewModel {

    public ObservableField<String> oldPsdField = new ObservableField<>("");

    public ObservableField<String> newPsdField = new ObservableField<>("");

    public ObservableField<String> confirmPsdField = new ObservableField<>("");

    /**
     * 提交
     */
    public BindingCommand<View> commitCommand = new BindingCommand<>(new BindingConsumerAction<View>() {
        @Override
        public void call(View view) {
            commit();
        }
    });

    public ChangePsdViewModel(@NonNull Application application) {
        super(application);
    }

    private void commit() {
        if (StringUtils.isEmpty(oldPsdField.get())) {
            ToastUtils.showToast("请输入原密码");
            return;
        }
        if (StringUtils.isEmpty(newPsdField.get())) {
            ToastUtils.showToast("请输入新密码");
            return;
        }
        if (StringUtils.isEmpty(confirmPsdField.get())) {
            ToastUtils.showToast("请确认新密码");
            return;
        }
        if (!newPsdField.get().equals(confirmPsdField.get())) {
            ToastUtils.showToast("两次输入密码不一致");
            return;
        }
        RxHttp.getApi(UserApi.class)
                .changePsd(oldPsdField.get(), newPsdField.get())
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(RxUtils.exceptionTransformer())
                .compose(getLifecycleProvider().<BaseEntity>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new EntityObserver<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        ToastUtils.showToast(entity.getMsg());
                        oldPsdField.set("");
                        newPsdField.set("");
                        confirmPsdField.set("");
                    }
                });
    }

}
