package com.muzi.repairtime.widget;

import android.app.Dialog;

/**
 * 作者: lipeng
 * 时间: 2019/1/26
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public abstract class SingleDialogHelper {

    private volatile int showNum = 0;

    public abstract Dialog getDialog();

    private Dialog dialog;

    public SingleDialogHelper() {

    }

    public void show() {
        if (dialog == null) {
            synchronized (SingleDialogHelper.this) {
                if (dialog == null) {
                    dialog = getDialog();
                }
            }
        }
        if (dialog == null) {
            check();
        }
        synchronized (SingleDialogHelper.this) {
            if (showNum == 0) {
                dialog.show();
            }
            showNum++;
        }
    }

    public void dismiss() {
        if (dialog == null) {
            check();
        }
        synchronized (SingleDialogHelper.this) {
            if (showNum > 0) {
                showNum--;
                if (showNum == 0) {
                    dialog.dismiss();
                }
            }
        }
    }

    public void release() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            showNum = 0;
            dialog = null;
        }
    }

    private void check() {
        if (dialog == null) {
            throw new NullPointerException("dialog为空");
        }
    }

}
