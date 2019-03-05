package com.muzi.repairtime.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.muzi.repairtime.R;


/**
 * 作者: lipeng
 * 时间: 2018/12/18
 * 邮箱: lipeng@moyi365.com
 * 功能: 网路进度条
 */
public class LoadingDialog extends Dialog {

    private Context context;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.CustomDialog);
        this.context = context;
        setContentView(R.layout.layout_dialog_progress);

        setCancelable(true);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
    }


    @Override
    public void show() {
        try {
            if (context != null && !((Activity) context).isFinishing()) {
                super.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            if (context != null && !((Activity) context).isFinishing()) {
                super.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
