package com.muzi.repairtime.fragment.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.databinding.FragmentStatisticApplyBinding;

/**
 * 作者: lipeng
 * 时间: 2019/3/25
 * 邮箱: lipeng@moyi365.com
 * 功能: 维修统计
 */
public class StatisticApplyFragment extends BaseFragment<FragmentStatisticApplyBinding, BaseViewModel> {

    public static StatisticApplyFragment getInstance() {
        StatisticApplyFragment fragment = new StatisticApplyFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_statistic_apply;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initView() {
        super.initView();
        binding.toolbar.setTitle("维修统计");
        initToolbarNav(binding.toolbar);
    }

}
