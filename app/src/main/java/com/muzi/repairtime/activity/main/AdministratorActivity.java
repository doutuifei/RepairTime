package com.muzi.repairtime.activity.main;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.muzi.repairtime.Constans;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.activity.login.LoginActivity;
import com.muzi.repairtime.data.DataProxy;
import com.muzi.repairtime.databinding.ActivityAdministratorBinding;
import com.muzi.repairtime.event.EventConstan;
import com.muzi.repairtime.event.LiveEventBus;
import com.muzi.repairtime.fragment.admin.PubNoticeFragment;
import com.muzi.repairtime.fragment.maintenance.AuditFragment;
import com.muzi.repairtime.fragment.psd.ChangePsdFragment;
import com.muzi.repairtime.fragment.user.UserInfoFragment;
import com.muzi.repairtime.utils.ToastUtils;
import com.muzi.repairtime.widget.dialog.CommonDialog;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能: 管理员
 */
public class AdministratorActivity extends BaseActivity<ActivityAdministratorBinding, BaseViewModel> implements BaseFragment.OnFragmentOpenDrawerListener {

    private SupportFragment[] fragments;
    private int[] ids;
    private int nextPosition, currePosition;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_administrator;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initData() {
        super.initData();
        binding.navEmployee.tvName.setText(DataProxy.getInstance().getString(Constans.KEY_USER));
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        LiveEventBus.get()
                .with(EventConstan.CHECK_ITEM, Integer.class)
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        if (integer >= ids.length) {
                            return;
                        }
                        int id = ids[integer];
                        binding.navEmployee.radiogroup.check(id);
                        nextPosition = integer;
                        showHideFragment(fragments[nextPosition], fragments[currePosition]);
                        currePosition = nextPosition;
                    }
                });
    }

    @Override
    public void initView() {
        super.initView();
        initFragment();
        initRadioGroup();
        binding.navEmployee.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CommonDialog.Builder(getContext())
                        .setTitle("提示")
                        .setContent("您确认注销当前用户吗?")
                        .build(new CommonDialog.OnDialogClickListener() {
                            @Override
                            public void onCancelClick(View v, Dialog dialog) {

                            }

                            @Override
                            public void onConfirmClick(View v, Dialog dialog) {
                                finish();
                                startActivity(new Intent(AdministratorActivity.this, LoginActivity.class));
                            }
                        }).show();
            }
        });
    }

    private void initFragment() {
        fragments = new SupportFragment[]{
                UserInfoFragment.getInstance(),
                AuditFragment.getInstance(),
                PubNoticeFragment.getInstance(),
                ChangePsdFragment.getInstance()
        };

        ids = new int[]{
                R.id.item_persion,
                R.id.item_audit,
                R.id.item_notice,
                R.id.item_psd
        };

        SupportFragment firstFragment = findFragment(fragments[currePosition].getClass());
        if (firstFragment == null) {
            loadMultipleRootFragment(R.id.container, currePosition, fragments);
        } else {
            for (int i = 0; i < fragments.length; i++) {
                fragments[i] = findFragment(fragments[i].getClass());
            }
        }
    }

    private void initRadioGroup() {
        binding.navEmployee.radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < ids.length; i++) {
                    if (checkedId == ids[i]) {
                        nextPosition = i;
                        showHideFragment(fragments[nextPosition], fragments[currePosition]);
                        currePosition = nextPosition;
                        break;
                    }
                }
                onCloseDrawer();
            }
        });
    }

    @Override
    public void onOpenDrawer() {
        if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void onCloseDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
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