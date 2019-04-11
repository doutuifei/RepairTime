package com.muzi.repairtime.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.muzi.repairtime.App;
import com.muzi.repairtime.R;
import com.muzi.repairtime.entity.RepairEntity;
import com.muzi.repairtime.utils.DateUtils;
import com.muzi.repairtime.utils.StringUtils;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能: 维修员接单
 */
public class ApplyTakeAdapter extends BaseQuickAdapter<RepairEntity.PagesBean.ListBean, BaseViewHolder> {

    public ApplyTakeAdapter(int layoutResId, @Nullable List<RepairEntity.PagesBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairEntity.PagesBean.ListBean item) {
        if (item.getOrderstatus().equals("未接单")) {
            helper.setTextColor(R.id.tv_content, App.getInstance().getResources().getColor(R.color.colorAccent));
        } else {
            helper.setTextColor(R.id.tv_content, App.getInstance().getResources().getColor(R.color.blue));
        }
        helper.setText(R.id.tv_content, StringUtils.maxString(item.getProblem(), 10));
        helper.setText(R.id.tv_author, item.getRepair_sec());
        helper.setText(R.id.tv_time, DateUtils.long2String(item.getReporttime()));
    }

}
