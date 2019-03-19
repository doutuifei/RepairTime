package com.muzi.repairtime.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.muzi.repairtime.R;
import com.muzi.repairtime.entity.RepairEntity;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能: 未完成
 */
public class ApplyUndoneAdapter extends BaseQuickAdapter<RepairEntity.PagesBean.ListBean, BaseViewHolder> {

    public ApplyUndoneAdapter(int layoutResId, @Nullable List<RepairEntity.PagesBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairEntity.PagesBean.ListBean item) {
        helper.setText(R.id.tv_project, item.getRepair_fir());
        helper.setText(R.id.tv_problem, item.getProblem());
        helper.addOnClickListener(R.id.btn_delete);
    }

}
