package com.muzi.repairtime.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.muzi.repairtime.R;
import com.muzi.repairtime.entity.NoticeEntity;
import com.muzi.repairtime.utils.DateUtils;
import com.muzi.repairtime.utils.StringUtils;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/14
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class NoticeAdapter extends BaseQuickAdapter<NoticeEntity.PagesBean.ListBean, BaseViewHolder> {

    public NoticeAdapter(int layoutResId, @Nullable List<NoticeEntity.PagesBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeEntity.PagesBean.ListBean item) {
        helper.setText(R.id.tv_content, StringUtils.maxString(item.getTitle(), 10));
        helper.setText(R.id.tv_author, item.getUsername());
        helper.setText(R.id.tv_time, DateUtils.long2String(item.getPublishtime()));
    }

}
