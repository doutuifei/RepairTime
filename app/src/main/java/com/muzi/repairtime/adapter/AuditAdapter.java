package com.muzi.repairtime.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.muzi.repairtime.R;
import com.muzi.repairtime.entity.AuditEntity;

import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/25
 * 邮箱: lipeng@moyi365.com
 * 功能: 人员审核
 */
public class AuditAdapter extends BaseMultiItemQuickAdapter<AuditEntity.PagesBean.ListBean, BaseViewHolder> {

    public AuditAdapter(List<AuditEntity.PagesBean.ListBean> data) {
        super(data);
        addItemType(0, R.layout.layout_item_audit_unchecked);
        addItemType(1, R.layout.layout_item_audit_checked);
        addItemType(2, R.layout.layout_item_audit_checked);
    }

    @Override
    protected void convert(BaseViewHolder helper, AuditEntity.PagesBean.ListBean item) {
        helper.setText(R.id.tv_name, item.getName() + "");
        helper.setText(R.id.tv_phone, item.getPhone() + "");
        helper.setText(R.id.tv_type, item.getGroup() + "");
        helper.setText(R.id.tv_role, item.getType() + "");

        switch (item.getItemType()) {
            case 0:
                helper.addOnClickListener(R.id.btn_pass);
                helper.addOnClickListener(R.id.btn_nopass);
                break;
            case 1:
                helper.setText(R.id.tv_status, "审核通过");
                break;
            case 2:
                helper.setText(R.id.tv_status, "审核不通过");
                break;
        }
    }
}
