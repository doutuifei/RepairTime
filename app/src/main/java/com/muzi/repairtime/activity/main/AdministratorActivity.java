package com.muzi.repairtime.activity.main;

import com.muzi.repairtime.R;
import com.muzi.repairtime.fragment.admin.PubNoticeFragment;
import com.muzi.repairtime.fragment.maintenance.AuditFragment;
import com.muzi.repairtime.fragment.psd.ChangePsdFragment;
import com.muzi.repairtime.fragment.user.UserInfoFragment;

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
        return R.menu.menu_administrator;
    }

    @Override
    public SupportFragment[] getFragments() {
        SupportFragment[] fragments = new SupportFragment[]{
                UserInfoFragment.getInstance(),
                AuditFragment.getInstance(),
                PubNoticeFragment.getInstance(),
                ChangePsdFragment.getInstance()
        };
        return fragments;
    }

    @Override
    public int[] getNavIds() {
        int[] ids = new int[]{
                R.id.nav_info,
                R.id.nav_audit,
                R.id.nav_notice,
                R.id.nav_change_psd
        };
        return ids;
    }

    @Override
    public int getFirst() {
        return 0;
    }

}