package com.muzi.repairtime.activity;

import android.util.Log;
import android.view.View;

import com.muzi.repairtime.Entity.BaseEntity;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.databinding.ActivityLoginBinding;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.LoginApi;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        getBinding().login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxHttp.getApi(LoginApi.class)
                        .login("15350831515", "123456")
                        .compose(RxUtils.<BaseEntity>scheduling())
                        .subscribe(new Observer<BaseEntity>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                showProgress();
                            }

                            @Override
                            public void onNext(BaseEntity entity) {
                                Log.e("LoginActivity", entity.toString());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                hideProgress();
                            }
                        });

            }
        });
    }


}
