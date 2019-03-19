package com.muzi.repairtime.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.muzi.repairtime.R;
import com.muzi.repairtime.entity.RepairEntity;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能: 维修员接单
 */
public class ApplyTakeAdapter extends BaseMultiItemQuickAdapter<RepairEntity.PagesBean.ListBean, BaseViewHolder> {

    public ApplyTakeAdapter(List<RepairEntity.PagesBean.ListBean> data) {
        super(data);
        //未接单
        addItemType(1, R.layout.layout_item_apply_take);
        //维修中
        addItemType(2, R.layout.layout_item_apply_take);
        //已完成
        addItemType(3, R.layout.layout_item_apply_take);
        //未完成
        addItemType(4, R.layout.layout_item_apply_take);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairEntity.PagesBean.ListBean item) {
        helper.setText(R.id.tv_project, item.getRepair_fir());
        helper.setText(R.id.tv_problem, item.getProblem());
        switch (item.getOrderstatus()) {
            case "未接单":
                helper.setText(R.id.btn_take, "接单");
                helper.addOnClickListener(R.id.btn_take);
                break;
            case "维修中":
                helper.setText(R.id.btn_take, item.getOrderstatus());
                break;
            case "未完成":
                helper.setText(R.id.btn_take, item.getOrderstatus());
                break;
            case "已完成":
                helper.setText(R.id.btn_take, item.getOrderstatus());
                helper.setText(R.id.tv_evaluate, item.getConsumersatisfaction());
                break;
        }
    }

}
