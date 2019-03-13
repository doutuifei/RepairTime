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

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 普通员工
 */
public abstract class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> implements NavigationView.OnNavigationItemSelectedListener {

    public abstract int getMenuId();

    public abstract SupportFragment[] getFragments();

    public abstract int[] getNavIds();

    public abstract int getFirst();

    private int nextPosition, currePosition = getFirst();

    private LoginEntity.UserBean userBean;

    private SupportFragment[] fragments;
    private int[] ids;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initParam(Bundle bundle) {
        super.initParam(bundle);
        userBean = bundle.getParcelable("user");
        fragments = getFragments();
        ids = getNavIds();
    }

    @Override
    public void initView() {
        super.initView();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        binding.navView.inflateMenu(getMenuId());

        initFragment();
    }

    @Override
    public void initData() {
        super.initData();
        binding.setUser(userBean);
    }

    private void initFragment() {
        SupportFragment firstFragment = findFragment(fragments[currePosition].getClass());
        if (firstFragment == null) {
            loadMultipleRootFragment(R.id.container, currePosition, fragments);
        } else {
            for (int i = 0; i < fragments.length; i++) {
                fragments[i] = findFragment(fragments[i].getClass());
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        for (int i = 0; i < ids.length; i++) {
            if (id == ids[i]) {
                nextPosition = i;
                showHideFragment(fragments[nextPosition], fragments[currePosition]);
                currePosition = nextPosition;
                break;
            }
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

    public LoginEntity.UserBean getUserBean() {
        return userBean;
    }

}
