package com.muzi.repairtime.fragment.apply;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.muzi.repairtime.BR;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.databinding.FragmentItemApplyBinding;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ApplyItemFragment extends BaseFragment<FragmentItemApplyBinding, ApplyItemViewModel> {

    private String status;

    public static ApplyItemFragment getInstance(String status) {
        ApplyItemFragment fragment = new ApplyItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_item_apply;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam(Bundle bundle) {
        super.initParam(bundle);
        status = bundle.getString("status");
    }

    @Override
    public void initView() {
        super.initView();
        binding.text.setText(status);
    }
}
