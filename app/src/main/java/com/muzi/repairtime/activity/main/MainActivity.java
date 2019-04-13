package com.muzi.repairtime.activity.main;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.muzi.repairtime.Constans;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.activity.login.LoginActivity;
import com.muzi.repairtime.data.DataProxy;
import com.muzi.repairtime.databinding.ActivityMainBinding;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.event.EventConstan;
import com.muzi.repairtime.event.LiveEventBus;
import com.muzi.repairtime.fragment.NoticeFragment;
import com.muzi.repairtime.fragment.admin.AuditFragment;
import com.muzi.repairtime.fragment.admin.PubNoticeFragment;
import com.muzi.repairtime.fragment.apply.ApplyFragment;
import com.muzi.repairtime.fragment.employee.AppliedFragment;
import com.muzi.repairtime.fragment.maintenance.ApplyListFragment;
import com.muzi.repairtime.fragment.psd.ChangePsdFragment;
import com.muzi.repairtime.fragment.user.UserInfoFragment;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.LoginApi;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.utils.ToastUtils;
import com.muzi.repairtime.widget.dialog.CommonDialog;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.json.JSONObject;

import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> implements BaseFragment.OnFragmentOpenDrawerListener {


    private SupportFragment[] fragments;
    private int[] ids;
    private String type;
    private String item = Constans.ITEM_INFO;
    private int nextPosition, currePosition = 0;

    private TextView tvName;
    private RadioGroup radioGroup;
    private Button btnLogout;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        type = DataProxy.getInstance().getString(Constans.KEY_TYPE, "");
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initData() {
        super.initData();
        String extraMap = getIntent().getStringExtra("extraMap");
        try {
            JSONObject jsonObject = new JSONObject(extraMap);
            item = jsonObject.optString("position", Constans.ITEM_INFO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        LiveEventBus.get()
                .with(EventConstan.CHECK_ITEM, Integer.class)
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer position) {
                        checkFragment(position);
                    }
                });
    }

    @Override
    public void initView() {
        super.initView();
        initViewStub();
        initFragment();
        initRadioGroup();
    }

    private void initViewStub() {
        switch (type) {
            case "普通用户":
                binding.viewStub.getViewStub().setLayoutResource(R.layout.layout_nav_employee);
                break;
            case "维修员":
                binding.viewStub.getViewStub().setLayoutResource(R.layout.layout_nav_maintenance);
                break;
            case "管理员":
                binding.viewStub.getViewStub().setLayoutResource(R.layout.layout_nav_administrator);
                break;
        }
        View viewStub = binding.viewStub.getViewStub().inflate();
        tvName = viewStub.findViewById(R.id.tv_name);
        radioGroup = viewStub.findViewById(R.id.radiogroup);
        btnLogout = viewStub.findViewById(R.id.btn_logout);

        tvName.setText(DataProxy.getInstance().getString(Constans.KEY_USER));
        btnLogout.setOnClickListener(new View.OnClickListener() {
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
                                logout();
                            }
                        }).show();
            }
        });
    }

    /**
     * 切换fragment
     *
     * @param position
     */
    private void checkFragment(int position) {
        if (position >= ids.length) {
            return;
        }
        int id = ids[position];
        radioGroup.check(id);
    }

    private void initFragment() {
        switch (type) {
            case "普通用户":
                fragments = new SupportFragment[]{
                        UserInfoFragment.getInstance(),
                        NoticeFragment.getInstance(),
                        ApplyFragment.getInstance(),
                        AppliedFragment.getInstance(),
                        ChangePsdFragment.getInstance()
                };

                ids = new int[]{
                        R.id.item_persion,
                        R.id.item_notice,
                        R.id.item_applying,
                        R.id.item_applied,
                        R.id.item_psd
                };
                switch (item) {
                    case Constans.ITEM_INFO:
                        currePosition = 0;
                        break;
                    case Constans.ITEM_NOTICE:
                        currePosition = 1;
                        break;
                    case Constans.ITEM_APPLYING:
                        currePosition = 2;
                        break;
                    case Constans.ITEM_APPLIED:
                        currePosition = 3;
                        break;
                    case Constans.ITEM_PSD:
                        currePosition = 4;
                        break;
                }
                break;
            case "维修员":
                fragments = new SupportFragment[]{
                        UserInfoFragment.getInstance(),
                        NoticeFragment.getInstance(),
                        ApplyListFragment.getInstance(),
                        ChangePsdFragment.getInstance()
                };

                ids = new int[]{
                        R.id.item_persion,
                        R.id.item_notice,
                        R.id.item_apply,
                        R.id.item_psd
                };
                switch (item) {
                    case Constans.ITEM_INFO:
                        currePosition = 0;
                        break;
                    case Constans.ITEM_NOTICE:
                        currePosition = 1;
                        break;
                    case Constans.ITEM_APPLYING:
                        currePosition = 2;
                        break;
                    case Constans.ITEM_PSD:
                        currePosition = 3;
                        break;
                }
                break;
            case "管理员":
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
                switch (item) {
                    case Constans.ITEM_INFO:
                        currePosition = 0;
                        break;
                    case Constans.ITEM_AUDIT:
                        currePosition = 1;
                        break;
                    case Constans.ITEM_NOTICE:
                        currePosition = 2;
                        break;
                    case Constans.ITEM_PSD:
                        currePosition = 3;
                        break;
                }
                break;
        }


        checkFragment(currePosition);

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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

    /**
     * 退出登录
     */
    private void logout() {
        RxHttp.getApi(LoginApi.class)
                .logout()
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new EntityObserver() {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        CloudPushService cloudPushService = PushServiceFactory.getCloudPushService();
                        cloudPushService.unbindAccount(new CommonCallback() {
                            @Override
                            public void onSuccess(String s) {

                            }

                            @Override
                            public void onFailed(String s, String s1) {

                            }
                        });
                        DataProxy.getInstance().remove(Constans.KEY_TYPE, Constans.KEY_USER);
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }
}
