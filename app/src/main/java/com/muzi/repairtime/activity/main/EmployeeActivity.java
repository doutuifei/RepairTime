package com.muzi.repairtime.activity.main;

import com.muzi.repairtime.R;
import com.muzi.repairtime.fragment.NoticeFragment;
import com.muzi.repairtime.fragment.apply.ApplyFragment;
import com.muzi.repairtime.fragment.employee.AppliedFragment;
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
        return R.menu.menu_employee;
    }

    @Override
    public SupportFragment[] getFragments() {
        SupportFragment[] fragments = new SupportFragment[]{
                UserInfoFragment.getInstance(),
                NoticeFragment.getInstance(),
                ApplyFragment.getInstance(),
                AppliedFragment.getInstance(),
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
                R.id.nav_applied,
                R.id.nav_change_psd
        };
        return ids;
    }

    @Override
    public int getFirst() {
        return 0;
    }

}
