package com.muzi.repairtime.observer;


import android.app.Activity;
import android.content.Intent;

import com.muzi.repairtime.Constans;
import com.muzi.repairtime.activity.base.IBaseView;
import com.muzi.repairtime.activity.login.LoginActivity;
import com.muzi.repairtime.data.DataProxy;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.exception.BaseException;
import com.muzi.repairtime.manager.AppManager;
import com.muzi.repairtime.utils.StringUtils;

/**
 * 作者: lipeng
 * 时间: 2019/1/4
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public abstract class EntityObserver<T extends BaseEntity> extends BaseObserver<T> {

    public EntityObserver() {
    }

    public EntityObserver(IBaseView baseView) {
        super(baseView);
    }

    @Override
    public void onNext(T entity) {
        super.onNext(entity);
        if (StringUtils.isNotEmpty(entity.getErrInfo())) {
            switch (entity.getErrInfo()) {
                case "0":
                    onSuccess(entity);
                    break;
                case "-1":
                    DataProxy.getInstance().remove(Constans.KEY_TYPE, Constans.KEY_USER);
                    Activity activity = AppManager.getAppManager().currentActivity();
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                    activity.finish();
                default:
                    onError(new BaseException(entity.getMsg()));
                    break;
            }
        } else {
            onSuccess(entity);
        }
    }

    public abstract void onSuccess(T t);

}
