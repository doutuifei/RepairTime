package com.muzi.repairtime.observer;


import com.muzi.repairtime.activity.base.IBaseView;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.utils.ToastUtils;

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
        switch (entity.getErrInfo()) {
            case "0":
                onSuccess(entity);
                break;
            default:
                onError(entity.getMsg());
                break;
        }
    }

    public abstract void onSuccess(T t);

    @Override
    public void onError(String msg) {
        super.onError(msg);
        ToastUtils.showToast(msg);
    }

}
