package com.muzi.repairtime.observer;

import com.muzi.repairtime.activity.base.IBaseView;
import com.muzi.repairtime.http.ErrorHandle;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EndConsumerHelper;

/**
 * 作者: lipeng
 * 时间: 2019/3/5
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public abstract class BaseObserver<T> implements Observer<T> {

    public BaseObserver() {
    }

    public BaseObserver(IBaseView baseView) {
        this.baseView = baseView;
    }

    private IBaseView baseView;

    private Disposable s;

    @Override
    public final void onSubscribe(@NonNull Disposable s) {
        if (EndConsumerHelper.validate(this.s, s, getClass())) {
            this.s = s;
            onStart();
        }
    }

    protected final void cancel() {
        Disposable s = this.s;
        this.s = DisposableHelper.DISPOSED;
        s.dispose();
        hideProgress();
    }

    protected void onStart() {
        showProgress();
    }

    @Override
    public void onError(Throwable e) {
        onError(ErrorHandle.dispatchException(e));
        onComplete();
    }

    public void onError(String msg) {

    }

    @Override
    public void onComplete() {
        hideProgress();
    }

    private void showProgress() {
        if (baseView != null) {
            baseView.showProgress();
        }
    }

    private void hideProgress() {
        if (baseView != null) {
            baseView.hideProgress();
        }
    }

}

