package com.muzi.repairtime.bindingadapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.muzi.repairtime.command.BindingCommand;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class RecyclerViewAdapter {

    @BindingAdapter(value = {"adapter"}, requireAll = false)
    public static void setAdapter(final RecyclerView recyclerView, final BindingCommand<Boolean> bindingCommand) {

    }

}
