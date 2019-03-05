package com.muzi.repairtime.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.muzi.repairtime.App;
import com.muzi.repairtime.R;


/**
 * 作者: lipeng
 * 时间: 2018/12/18
 * 邮箱: lipeng@moyi365.com
 * 功能: Toast工具类
 */
public class ToastUtils {

    private static EkwToast toast;

    public static void showToast(Context context, String content) {
        if (StringUtils.isEmpty(content))
            return;
        if (toast == null) {
            toast = new EkwToast(context);
        }
        toast.setText(content);
        toast.show();
    }

    public static void showToast(Context context, @StringRes int id) {
        showToast(context, context.getResources().getString(id));
    }

    public static void showToast(@StringRes int id) {
        showToast(App.getInstance().getApplicationContext(), id);
    }

    public static void showToast(String content) {
        showToast(App.getInstance().getApplicationContext(), content);
    }

    public static class EkwToast extends Toast {

        private TextView txMsg;

        public EkwToast(Context context) {
            super(context);
            View view = View.inflate(context, R.layout.layout_toast, null);
            txMsg = view.findViewById(R.id.tx_msg);
            setView(view);
            setDuration(Toast.LENGTH_SHORT);
            setGravity(Gravity.CENTER, 0, 0);
        }

        @Override
        public void setText(CharSequence s) {
            txMsg.setText(s);
        }

        @Override
        public void setText(int resId) {
            txMsg.setText(resId);
        }
    }

}
