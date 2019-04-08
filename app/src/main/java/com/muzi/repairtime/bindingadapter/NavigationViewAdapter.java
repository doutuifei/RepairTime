package com.muzi.repairtime.bindingadapter;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;

import com.muzi.repairtime.R;
import com.muzi.repairtime.databinding.NavHeaderMainBinding;
import com.muzi.repairtime.entity.LoginEntity;

/**
 * 作者: lipeng
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class NavigationViewAdapter {

    /**
     * NavigationView 添加head，注入mode
     *
     * @param view
     * @param userBean
     */
    @BindingAdapter(value = {"user"})
    public static void loadHeader(NavigationView view, LoginEntity.UserBean userBean) {
        NavHeaderMainBinding binding = DataBindingUtil.inflate(LayoutInflater.from(view.getContext()), R.layout.nav_header_main, view, false);
        binding.setUser(userBean);
        binding.executePendingBindings();
        view.addHeaderView(binding.getRoot());
    }

}

