package com.muzi.repairtime.activity.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.databinding.ActivityMainBinding;
import com.muzi.repairtime.entity.LoginEntity;
import com.muzi.repairtime.fragment.AppliedFragment;
import com.muzi.repairtime.fragment.ApplyFragment;
import com.muzi.repairtime.fragment.ChangePsdFragment;
import com.muzi.repairtime.fragment.UserInfoFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 普通员工
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> implements NavigationView.OnNavigationItemSelectedListener {

    private int nextPosition, currePosition = 0;

    private LoginEntity.UserBean userBean;

    private SupportFragment[] fragments;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initParam() {
        super.initParam();
        userBean = getIntent().getExtras().getParcelable("user");
    }

    @Override
    public void initView() {
        super.initView();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        switch (userBean.getType()) {
            case "普通用户":
                binding.navView.inflateMenu(R.menu.activity_main_drawer);
                initEmployee();
                break;
            case "维修员":

                break;
            case "管理员":

                break;
        }

    }

    @Override
    public void initData() {
        super.initData();
        binding.setUser(userBean);
    }

    /**
     * 初始化普通员工界面
     */
    private void initEmployee() {
        SupportFragment userInfoFragment = findFragment(UserInfoFragment.class);
        fragments = new SupportFragment[4];
        if (userInfoFragment == null) {
            fragments[0] = UserInfoFragment.getInstance();
            fragments[1] = ApplyFragment.getInstance();
            fragments[2] = AppliedFragment.getInstance();
            fragments[3] = ChangePsdFragment.getInstance();
            loadMultipleRootFragment(R.id.container, currePosition,
                    fragments[0],
                    fragments[1],
                    fragments[2],
                    fragments[3]);
        } else {
            fragments[0] = userInfoFragment;
            fragments[1] = findFragment(ApplyFragment.class);
            fragments[2] = findFragment(AppliedFragment.class);
            fragments[3] = findFragment(ChangePsdFragment.class);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_info:
                //个人信息
                nextPosition = 0;
                showHideFragment(fragments[nextPosition], fragments[currePosition]);
                currePosition = nextPosition;
                break;
            case R.id.nav_apply:
                //维修申请
                nextPosition = 1;
                showHideFragment(fragments[nextPosition], fragments[currePosition]);
                currePosition = nextPosition;
                break;
            case R.id.nav_applied:
                //我的申请
                nextPosition = 2;
                showHideFragment(fragments[nextPosition], fragments[currePosition]);
                currePosition = nextPosition;
                break;
            case R.id.nav_change_psd:
                //修改密码
                nextPosition = 3;
                showHideFragment(fragments[nextPosition], fragments[currePosition]);
                currePosition = nextPosition;
                break;
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressedSupport() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressedSupport();
        }
    }

}
