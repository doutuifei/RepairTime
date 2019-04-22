package com.muzi.repairtime.activity.login;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.muzi.repairtime.App;
import com.muzi.repairtime.BR;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.databinding.ActivityLoginBinding;
import com.muzi.repairtime.widget.dialog.CommonDialog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

/**
 * 登录activity
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initView() {
        super.initView();
        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        App.getInstance().copyRing();
                    }
                })
                .start();

        AndPermission.with(this)
                .notification()
                .permission()
                .rationale(new Rationale<Void>() {
                    @Override
                    public void showRationale(Context c, Void d, final RequestExecutor e) {
                        // 没有权限会调用该访问，开发者可以在这里弹窗告知用户无权限。
                        // 启动设置: e.execute();
                        // 取消启动: e.cancel();
                        new CommonDialog.Builder(c)
                                .setCancelName("取消")
                                .setConfirmName("设置")
                                .setTitle("提示")
                                .setContent("当前没有通知栏权限，无法收到消息通知，是否去开启？")
                                .build(new CommonDialog.OnDialogClickListener() {
                                    @Override
                                    public void onCancelClick(View v, Dialog dialog) {
                                        e.cancel();
                                    }

                                    @Override
                                    public void onConfirmClick(View v, Dialog dialog) {
                                        e.execute();
                                    }
                                }).show();
                    }
                })
                .start();
    }

}
