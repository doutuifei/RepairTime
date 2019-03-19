package com.muzi.repairtime.fragment.employee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.adapter.ViewPagerAdapter;
import com.muzi.repairtime.databinding.FragmentAppliedBinding;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能: 我的申请
 */
public class AppliedFragment extends BaseFragment<FragmentAppliedBinding, BaseViewModel> {

    private String[] titles = new String[5];
    private Fragment[] fragments = new Fragment[5];
    private ViewPagerAdapter viewPagerAdapter;

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
        return 0;
    }

    @Override
    public void initData() {
        super.initData();
        titles[0] = "全部";
        titles[1] = "未接单";
        titles[2] = "已完成";
        titles[3] = "未完成";
        titles[4] = "维修中";

        fragments[0] = ApplyItemFragment.getInstance("");
        fragments[1] = ApplyUndoneFragment.getInstance("1");
        fragments[2] = ApplyItemFragment.getInstance("3");
        fragments[3] = ApplyItemFragment.getInstance("4");
        fragments[4] = ApplyRepairingFragment.getInstance("2");
    }

    @Override
    public void initView() {
        super.initView();
        binding.toolbar.setTitle("我的申请");
        initToolbarNav(binding.toolbar);
        for (String title : titles) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(title));
        }
        binding.viewPager.setOffscreenPageLimit(5);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), titles, fragments);
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

}
