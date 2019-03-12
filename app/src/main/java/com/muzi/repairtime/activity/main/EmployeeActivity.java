package com.muzi.repairtime.activity.main;

import com.muzi.repairtime.R;
import com.muzi.repairtime.fragment.AppliedFragment;
import com.muzi.repairtime.fragment.apply.ApplyFragment;
import com.muzi.repairtime.fragment.psd.ChangePsdFragment;
import com.muzi.repairtime.fragment.user.UserInfoFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能: 普通员工
 */
public class EmployeeActivity extends MainActivity {

    @Override
    public int getMenuId() {
        return R.menu.activity_main_drawer;
    }

    @Override
    public SupportFragment[] getFragments() {
        SupportFragment[] fragments = new SupportFragment[4];
        fragments[0] = UserInfoFragment.getInstance();
        fragments[1] = ApplyFragment.getInstance();
        fragments[2] = AppliedFragment.getInstance();
        fragments[3] = ChangePsdFragment.getInstance();
        return fragments;
    }

    @Override
    public int[] getNavIds() {
        int[] ids = new int[4];
        ids[0] = R.id.nav_info;
        ids[1] = R.id.nav_apply;
        ids[2] = R.id.nav_applied;
        ids[3] = R.id.nav_change_psd;
        return ids;
    }

    @Override
    public int getFirst() {
        return 0;
    }

}
