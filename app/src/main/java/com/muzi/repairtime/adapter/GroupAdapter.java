package com.muzi.repairtime.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.muzi.repairtime.R;
import com.muzi.repairtime.entity.GroupEntity;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/7
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class GroupAdapter extends BaseQuickAdapter<GroupEntity.PagesBean, BaseViewHolder> {

    public GroupAdapter(int layoutResId, @Nullable List<GroupEntity.PagesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupEntity.PagesBean item) {
        helper.setText(R.id.text, item.getName());
    }

}
