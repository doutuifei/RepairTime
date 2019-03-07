package com.muzi.repairtime.activity.register;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.command.BindingCommand;
import com.muzi.repairtime.command.BindingConsumerAction;
import com.muzi.repairtime.entity.GroupEntity;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.LoginApi;
import com.muzi.repairtime.observer.BaseObserver;
import com.muzi.repairtime.utils.ToastUtils;
import com.muzi.repairtime.widget.dialog.GroupDialog;

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

    public ObservableField<String> group = new ObservableField<>("");

    /**
     * 科室信息
     */
    private GroupEntity groupEntity;
    private GroupDialog groupDialog;

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

    /**
     * 获取科室
     *
     * @param view
     */
    public void getGroups(View view) {
        if (groupEntity == null) {
            RxHttp.getApi(LoginApi.class)
                    .getGroups()
                    .compose(RxUtils.<GroupEntity>scheduling())
                    .compose(RxUtils.<GroupEntity>bindToLifecycle(getLifecycleProvider()))
                    .subscribe(new BaseObserver<GroupEntity>() {
                        @Override
                        public void onNext(GroupEntity entity) {
                            groupEntity = entity;
                            showGroupDialog();
                        }

                        @Override
                        public void onError(String msg) {
                            super.onError(msg);
                            ToastUtils.showToast(msg);
                        }
                    });
        } else {
            showGroupDialog();
        }
    }

    private void showGroupDialog() {
        if (groupDialog == null) {
            groupDialog = new GroupDialog(getContext(), groupEntity) {
                @Override
                public void onSelect(GroupEntity.PagesBean pagesBean) {
                    group.set(pagesBean.getName());
                }
            };
        }
        groupDialog.show();
    }

}
