package com.muzi.repairtime.bindingadapter;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding2.view.RxView;
import com.muzi.repairtime.command.BindingCommand;
import com.muzi.repairtime.utils.ViewUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * 作者: lipeng
 * 时间: 2019/3/6
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ViewAdapter {

    //防重复点击间隔(秒)
    public static final int CLICK_INTERVAL = 1;

    /**
     * 点击事件，可以拦截处理多次点击
     *
     * @param view
     * @param command
     */
    @BindingAdapter(value = {"onClickCommand", "isThrottleFirst"}, requireAll = false)
    public static void onClickCommand(final View view, final BindingCommand<View> command, final boolean isThrottleFirst) {
        if (isThrottleFirst) {
            RxView.clicks(view)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            if (command != null) {
                                command.execute(view);
                            }
                        }
                    });
        } else {
            RxView.clicks(view)
                    .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object object) throws Exception {
                            if (command != null) {
                                command.execute(view);
                            }
                        }
                    });
        }
    }

    /**
     * 焦点变化
     *
     * @param view
     * @param bindingCommand
     */
    @BindingAdapter(value = {"onFocusChanged"}, requireAll = false)
    public static void setOnFocusChanged(final View view, final BindingCommand<Boolean> bindingCommand) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (bindingCommand != null) {
                    bindingCommand.execute(hasFocus);
                }
                if (hasFocus) {
                    if (view instanceof EditText) {
                        ViewUtils.setSelection((EditText) view);
                    }
                }
            }
        });
    }

}
