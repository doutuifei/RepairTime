package com.muzi.repairtime.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.muzi.repairtime.BR;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.databinding.FragmentUserinfoBinding;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能: 用户信息
 */
public class UserInfoFragment extends BaseFragment<FragmentUserinfoBinding, UserInfoViewModel> {

    public static UserInfoFragment getInstance() {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_userinfo;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

}
