package com.muzi.repairtime.bindingadapter;

import android.databinding.BindingAdapter;
import android.view.View;

import com.muzi.repairtime.command.BindingCommand;

/**
 * 作者: lipeng
 * 时间: 2019/3/25
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ToolbarAdapter {

    @BindingAdapter(value = {"navigationOnClick"}, requireAll = false)
    public static void navigationOnClick(final android.support.v7.widget.Toolbar toolbar, final BindingCommand<View> command) {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (command != null) {
                    command.execute(v);
                }
            }
        });
    }

}
