package com.muzi.repairtime.fragment.user;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.entity.UserEntity;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.UserApi;
import com.muzi.repairtime.observer.BaseObserver;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class UserInfoViewModel extends BaseViewModel {

    /**
     * 姓名
     */
    public ObservableField<String> nameField = new ObservableField<>();

    /**
     * 科室
     */
    public ObservableField<String> groupField = new ObservableField<>();

    /**
     * 手机号
     */
    public ObservableField<String> phoneField = new ObservableField<>();

    /**
     * ip
     */
    public ObservableField<String> ipField = new ObservableField<>();

    /**
     * 用户类型
     */
    public ObservableField<String> typeField = new ObservableField<>();

    /**
     * 负责人
     */
    public ObservableField<String> principalField = new ObservableField<>();

    public UserInfoViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void initContext(Context context) {
        super.initContext(context);
        RxHttp.getApi(UserApi.class)
                .getUserInfo()
                .compose(RxUtils.<UserEntity>scheduling())
                .compose(RxUtils.<UserEntity>bindToLifecycle(getLifecycleProvider()))
                .subscribe(new BaseObserver<UserEntity>(this) {
                    @Override
                    public void onNext(UserEntity entity) {
                        super.onNext(entity);
                        nameField.set(entity.getPages().getName());
                        groupField.set(entity.getPages().getGroup());
                        phoneField.set(entity.getPages().getPhone());
                        ipField.set(entity.getPages().getIpAddress());
                        typeField.set(entity.getPages().getType());
                        principalField.set(entity.getPages().getPic());
                    }
                });
    }

}
