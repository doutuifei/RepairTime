package com.muzi.repairtime.fragment.psd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.muzi.repairtime.BR;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.databinding.FragmentChangePsdBinding;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能: 修改密码
 */
public class ChangePsdFragment extends BaseFragment<FragmentChangePsdBinding,ChangePsdViewModel> {

    public static ChangePsdFragment getInstance() {
        ChangePsdFragment fragment = new ChangePsdFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_change_psd;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initView() {
        super.initView();
        binding.toolbar.setTitle("修改密码");
        initToolbarNav(binding.toolbar);
    }

}
