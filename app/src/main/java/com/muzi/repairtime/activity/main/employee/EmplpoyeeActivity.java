package com.muzi.repairtime.activity.main.employee;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.muzi.repairtime.BR;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.databinding.ActivityMainBinding;
import com.muzi.repairtime.event.EventConstan;
import com.muzi.repairtime.event.LiveEventBus;
import com.muzi.repairtime.fragment.AppliedFragment;
import com.muzi.repairtime.fragment.ApplyFragment;
import com.muzi.repairtime.fragment.ChangePsdFragment;
import com.muzi.repairtime.fragment.UserInfoFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 普通员工
 */
public class EmplpoyeeActivity extends BaseActivity<ActivityMainBinding, EmplpoyeeViewModel> implements NavigationView.OnNavigationItemSelectedListener {

    private int nextPosition, currePosition = 0;

    private SupportFragment[] fragments = new SupportFragment[4];

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initView() {
        super.initView();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        initFragment();
    }

    private void initFragment() {
        SupportFragment userInfoFragment = findFragment(UserInfoFragment.class);
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

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        /**
         * 用户
         */
        LiveEventBus.get().with(EventConstan.USER_INFO, String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
//                        binding.navView.getHeaderView(0)
                    }
                });
    }
}
