package com.muzi.repairtime.activity.base;

/**
 * 作者: lipeng
 * 时间: 2019/3/5
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public interface IBaseActivity {

    /**
     * 初始化界面传递参数
     */
    void initParam();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化view
     */
    void initView();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();

}
