package com.muzi.repairtime.activity.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.muzi.repairtime.widget.LoadingDialog;
import com.muzi.repairtime.widget.SingleDialogHelper;
import com.trello.rxlifecycle2.components.RxActivity;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * 作者: lipeng
 * 时间: 2019/3/4
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public abstract class BaseActivity<V extends ViewDataBinding> extends RxActivity implements IBaseView {

    private SingleDialogHelper singleDialogHelper;

    private V v;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = DataBindingUtil.setContentView(this, getLayoutId());
        init();
    }

    public abstract int getLayoutId();

    protected abstract void init();

    public V getBinding() {
        return v;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgress() {
        if (singleDialogHelper == null) {
            singleDialogHelper = new SingleDialogHelper() {
                @Override
                public Dialog getDialog() {
                    return new LoadingDialog(getContext());
                }
            };
        }
        singleDialogHelper.show();
    }

    @Override
    public void hideProgress() {
        if (singleDialogHelper != null) {
            singleDialogHelper.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (singleDialogHelper != null) {
            singleDialogHelper.release();
        }
        if (v != null) {
            v.unbind();
        }
    }

}
