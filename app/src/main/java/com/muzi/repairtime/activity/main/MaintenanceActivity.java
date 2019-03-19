package com.muzi.repairtime.activity.main;

import com.muzi.repairtime.R;
import com.muzi.repairtime.fragment.NoticeFragment;
import com.muzi.repairtime.fragment.maintenance.ApplyListFragment;
import com.muzi.repairtime.fragment.psd.ChangePsdFragment;
import com.muzi.repairtime.fragment.user.UserInfoFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能: 维修员
 */
public class MaintenanceActivity extends MainActivity {

    @Override
    public int getMenuId() {
        return R.menu.menu_maintenance;
    }

    @Override
    public SupportFragment[] getFragments() {
        SupportFragment[] fragments = new SupportFragment[]{
                UserInfoFragment.getInstance(),
                NoticeFragment.getInstance(),
                ApplyListFragment.getInstance(),
                ChangePsdFragment.getInstance()
        };
        return fragments;
    }

    @Override
    public int[] getNavIds() {
        int[] ids = new int[]{
                R.id.nav_info,
                R.id.nav_notice,
                R.id.nav_apply,
                R.id.nav_change_psd
        };
        return ids;
    }


    @Override
    public int getFirst() {
        return 0;
    }

}