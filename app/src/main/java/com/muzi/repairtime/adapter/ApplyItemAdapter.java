package com.muzi.repairtime.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.muzi.repairtime.R;
import com.muzi.repairtime.entity.RepairEntity;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ApplyItemAdapter extends BaseMultiItemQuickAdapter<RepairEntity.PagesBean.ListBean, BaseViewHolder> {

    private onRatingBar onRatingBar;

    public ApplyItemAdapter(List<RepairEntity.PagesBean.ListBean> data) {
        super(data);
        //未接单
        addItemType(1, R.layout.layout_item_apply_untake);
        //维修中
        addItemType(2, R.layout.layout_item_apply_repairing);
        //已完成
        addItemType(3, R.layout.layout_item_apply_finished);
        //未完成
        addItemType(4, R.layout.layout_item_apply_unfinished);
    }

    public void setOnRatingBar(onRatingBar onRatingBar) {
        this.onRatingBar = onRatingBar;
    }

    @Override
    protected void convert(final BaseViewHolder helper, RepairEntity.PagesBean.ListBean item) {
        helper.setText(R.id.tv_project, item.getRepair_fir());
        helper.setText(R.id.tv_problem, item.getProblem());
        switch (item.getItemType()) {
            case 1:
                //未接单
                helper.addOnClickListener(R.id.btn_delete);
                break;
            case 2:
                //维修中
                helper.addOnClickListener(R.id.btn_finished);
                helper.addOnClickListener(R.id.btn_unfinished);
                break;
            case 3:
                //已完成
                /**
                 * 没有评价
                 */
                MaterialRatingBar ratingBar = helper.getView(R.id.ratingBar);
                if (item.getCs_id() == -1) {
                    ratingBar.setProgress(0);
                    if (ratingBar.getOnRatingChangeListener() == null) {
                        ratingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
                            @Override
                            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                                int position = helper.getLayoutPosition();
                                if (onRatingBar != null) {
                                    onRatingBar.rating(position, rating);
                                }
                            }
                        });
                    }
                    ratingBar.setEnabled(true);
                    helper.setGone(R.id.tv_ratingBar, true);
                } else {
                    ratingBar.setOnRatingChangeListener(null);
                    ratingBar.setProgress(item.getCs_id());
                    helper.setGone(R.id.tv_ratingBar, false);
                    ratingBar.setEnabled(false);
                }
                break;
            case 4:
                //未完成
                break;
        }
    }

    public interface onRatingBar {

        void rating(int position, float rating);

    }

}
