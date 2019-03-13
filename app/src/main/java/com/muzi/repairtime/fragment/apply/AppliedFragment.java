package com.muzi.repairtime.fragment.apply;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.muzi.repairtime.BR;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.databinding.FragmentAppliedBinding;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能: 我的申请
 */
public class AppliedFragment extends BaseFragment<FragmentAppliedBinding, AppliedViewModel> {

    public static AppliedFragment getInstance() {
        AppliedFragment fragment = new AppliedFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_applied;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

}
