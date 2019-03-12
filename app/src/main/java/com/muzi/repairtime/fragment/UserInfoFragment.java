package com.muzi.repairtime.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.RxFragment;
import com.muzi.repairtime.entity.UserEntity;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.UserApi;

import io.reactivex.functions.Consumer;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能: 用户信息
 */
public class UserInfoFragment extends RxFragment {

    public static UserInfoFragment getInstance() {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_userinfo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        RxHttp.getApi(UserApi.class)
                .getUserInfo()
                .compose(RxUtils.<UserEntity>scheduling())
                .compose(RxUtils.<UserEntity>bindToLifecycle(this))
                .subscribe(new Consumer<UserEntity>() {
                    @Override
                    public void accept(UserEntity userEntity) throws Exception {

                    }
                });
    }

}
