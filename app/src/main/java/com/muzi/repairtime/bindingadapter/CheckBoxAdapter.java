package com.muzi.repairtime.bindingadapter;

import android.databinding.BindingAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.muzi.repairtime.command.BindingCommand;

/**
 * 作者: lipeng
 * 时间: 2019/3/8
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class CheckBoxAdapter {

    /**
     * CheckBox切换
     *
     * @param checkBox
     * @param bindingCommand
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"onCheckedChange"}, requireAll = false)
    public static void setCheckedChanged(final CheckBox checkBox, final BindingCommand<Boolean> bindingCommand) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (bindingCommand != null) {
                    bindingCommand.execute(b);
                }
            }
        });
    }

}
