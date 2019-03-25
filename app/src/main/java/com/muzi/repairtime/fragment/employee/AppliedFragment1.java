package com.muzi.repairtime.fragment.employee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.databinding.FragmentApplied1Binding;
import com.muzi.repairtime.widget.ApplyItemPop;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能: 我的申请
 */
public class AppliedFragment1 extends BaseFragment<FragmentApplied1Binding, BaseViewModel> {

    private ApplyItemPop applyItemPop;

    private SupportFragment[] fragments = new SupportFragment[5];

    public static AppliedFragment1 getInstance() {
        AppliedFragment1 fragment = new AppliedFragment1();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_applied1;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initView() {
        super.initView();
        binding.toolbar.setTitle("我的申请");
        initToolbarNav(binding.toolbar);

        initFragment();

        binding.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop();
            }
        });
    }

    private void initFragment() {
        fragments[0] = ApplyItemFragment.getInstance("");
        fragments[1] = ApplyItemFragment.getInstance("1");
        fragments[2] = ApplyItemFragment.getInstance("3");
        fragments[3] = ApplyItemFragment.getInstance("4");
        fragments[4] = ApplyItemFragment.getInstance("2");
        loadMultipleRootFragment(R.id.container, 0,
                fragments[0],
                fragments[1],
                fragments[2],
                fragments[3],
                fragments[4]);
    }

    private void showPop() {
        if (applyItemPop == null) {
            applyItemPop = new ApplyItemPop(getContext());
            applyItemPop.setChangeListener(new ApplyItemPop.CheckedChangeListener() {
                @Override
                public void onChecked(int position) {
                    showHideFragment(fragments[position]);
                }
            });
        }
        applyItemPop.showAsDropDown(binding.btnMore, -20, 20);
    }

}
