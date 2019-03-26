package com.muzi.repairtime.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.muzi.repairtime.R;
import com.muzi.repairtime.entity.NoticeEntity;
import com.muzi.repairtime.interfaces.onSwipeListener;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/14
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class NoticeAdminAdapter extends NoticeAdapter {

    public NoticeAdminAdapter(int layoutResId, @Nullable List<NoticeEntity.PagesBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, NoticeEntity.PagesBean.ListBean item) {
        super.convert(helper, item);
        helper.getView(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSwipeListener!=null){
                    mOnSwipeListener.onDelete(helper.getLayoutPosition());
                }
            }
        });
    }

    private onSwipeListener mOnSwipeListener;

    public void setOnDelListener(onSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }
}
