package com.muzi.repairtime.activity.main;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.databinding.ActivityMainBinding;
import com.muzi.repairtime.entity.LoginEntity;
import com.muzi.repairtime.event.EventConstan;
import com.muzi.repairtime.event.LiveEventBus;
import com.muzi.repairtime.utils.ToastUtils;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 普通员工
 */
public abstract class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> implements NavigationView.OnNavigationItemSelectedListener, BaseFragment.OnFragmentOpenDrawerListener {

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

        binding.navView.setNavigationItemSelectedListener(this);

        binding.navView.inflateMenu(getMenuId());

        initFragment();
    }

    @Override
    public void initData() {
        super.initData();
        binding.setUser(userBean);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        LiveEventBus.get()
                .with(EventConstan.CHECK_ITEM, Integer.class)
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        Log.e("MainActivity", "integer:" + integer);
                        if (integer > ids.length) {
                            return;
                        }
                        int id = ids[integer];
                        binding.navView.setCheckedItem(id);
                    }
                });
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
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onOpenDrawer() {
        if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
