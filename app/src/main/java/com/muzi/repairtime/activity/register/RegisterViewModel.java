package com.muzi.repairtime.activity.register;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.entity.GroupEntity;
import com.muzi.repairtime.event.EventConstan;
import com.muzi.repairtime.event.LiveEventBus;
import com.muzi.repairtime.exception.PhoneExistException;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.LoginApi;
import com.muzi.repairtime.observer.BaseObserver;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.utils.StringUtils;
import com.muzi.repairtime.utils.ToastUtils;
import com.muzi.repairtime.widget.dialog.GroupDialog;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

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
        if (StringUtils.isEmpty(userName.get())) {
            ToastUtils.showToast("请输入姓名");
            return;
        }
        if (StringUtils.isEmpty(phone.get())) {
            ToastUtils.showToast("请输入手机号");
            return;
        }
        if (!StringUtils.isPhone(phone.get())) {
            ToastUtils.showToast("请输入正确的手机号");
            return;
        }
        if (StringUtils.isEmpty(password.get())) {
            ToastUtils.showToast("请输入密码");
            return;
        }
        if (StringUtils.isEmpty(confirmPsd.get())) {
            ToastUtils.showToast("请再次输入密码");
            return;
        }
        if (!confirmPsd.get().equals(password.get())) {
            ToastUtils.showToast("两次输入密码不一致");
            return;
        }
        if (StringUtils.isEmpty(group.get())) {
            ToastUtils.showToast("请选择科室");
            return;
        }
        /**
         * 判断账号是注册，并注册账号
         */
        RxHttp.getApi(LoginApi.class)
                .decidePhone(phone.get())
                .flatMap(new Function<BaseEntity, ObservableSource<BaseEntity>>() {
                    @Override
                    public ObservableSource<BaseEntity> apply(BaseEntity entity) throws Exception {
                        if ("1".equals(entity.getErrInfo())) {
                            throw new PhoneExistException(entity.getMsg());
                        } else {
                            return RxHttp.getApi(LoginApi.class)
                                    .register(userName.get(),
                                            phone.get(),
                                            password.get(),
                                            group.get());
                        }
                    }
                })
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(RxUtils.<BaseEntity>bindToLifecycle(getLifecycleProvider()))
                .subscribe(new EntityObserver<BaseEntity>() {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        ToastUtils.showToast(entity.getMsg());
                        LiveEventBus.get()
                                .with(EventConstan.ACCOUNT)
                                .setValue(new String[]{phone.get(), password.get()});
                        finish();
                    }
                });
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

    /**
     * 选择科室dialog
     */
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        groupDialog = null;
        groupEntity = null;
    }

}
