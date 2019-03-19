package com.muzi.repairtime.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.muzi.repairtime.R;
import com.muzi.repairtime.entity.RepairEntity;
import com.muzi.repairtime.utils.StringUtils;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能: 已完成
 */
public class ApplyFinishedAdapter extends BaseQuickAdapter<RepairEntity.PagesBean.ListBean, BaseViewHolder> {

    private onRatingBar onRatingBar;

    public void setOnRatingBar(ApplyFinishedAdapter.onRatingBar onRatingBar) {
        this.onRatingBar = onRatingBar;
    }

    public ApplyFinishedAdapter(int layoutResId, @Nullable List<RepairEntity.PagesBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, RepairEntity.PagesBean.ListBean item) {
        helper.setText(R.id.tv_project, item.getRepair_fir());
        helper.setText(R.id.tv_problem, item.getProblem());

        /**
         * 没有评价
         */
        MaterialRatingBar ratingBar = helper.getView(R.id.ratingBar);
        if (StringUtils.isEmpty(item.getConsumersatisfaction())) {
            ratingBar.setProgress(0);
            ratingBar.setEnabled(true);
            helper.setGone(R.id.tv_ratingBar, true);
            ratingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
                @Override
                public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                    if (onRatingBar != null) {
                        onRatingBar.rating(helper.getLayoutPosition(), rating);
                    }
                }
            });
        } else {
            helper.setGone(R.id.tv_ratingBar, false);
            ratingBar.setProgress(item.getCs_id());
            ratingBar.setEnabled(false);
        }
    }

    public interface onRatingBar {

        void rating(int position, float rating);

    }

}
