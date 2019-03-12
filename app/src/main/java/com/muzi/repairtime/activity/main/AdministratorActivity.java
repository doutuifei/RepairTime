package com.muzi.repairtime.activity.main;

import com.muzi.repairtime.R;
import com.muzi.repairtime.fragment.AppliedFragment;
import com.muzi.repairtime.fragment.ApplyFragment;
import com.muzi.repairtime.fragment.ChangePsdFragment;
import com.muzi.repairtime.fragment.UserInfoFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能: 管理员
 */
public class AdministratorActivity extends MainActivity {

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
        ids[0] = R.id.nav_apply;
        ids[0] = R.id.nav_applied;
        ids[0] = R.id.nav_change_psd;
        return ids;
    }

    @Override
    public int getFirst() {
        return 0;
    }

}