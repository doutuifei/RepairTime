package com.muzi.repairtime.activity.applydetail;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.muzi.repairtime.BR;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.databinding.ActivityApplyItemDetailBinding;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.entity.RepairEntity;
import com.muzi.repairtime.event.EventConstan;
import com.muzi.repairtime.event.LiveEventBus;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.RepairApi;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.utils.ToastUtils;
import com.muzi.repairtime.widget.dialog.CommonDialog;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ApplyItemDetailActivity extends BaseActivity<ActivityApplyItemDetailBinding, ApplyItemDetailViewModel> {

    public static void startActivity(Context context, RepairEntity.PagesBean.ListBean listBean) {
        Intent intent = new Intent(context, ApplyItemDetailActivity.class);
        intent.putExtra("bean", listBean);
        context.startActivity(intent);
    }

    private RepairEntity.PagesBean.ListBean listBean;

    private int evaluate;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_apply_item_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam(Bundle bundle) {
        super.initParam(bundle);
        listBean = bundle.getParcelable("bean");
        evaluate = listBean.getCs_id();
    }

    @Override
    public void initView() {
        super.initView();
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (evaluate > 0) {
            binding.ratingBar.setEnabled(false);
            binding.ratingBar.setProgress(evaluate);
            binding.tvEvaluate.setVisibility(View.GONE);
        } else {
            binding.ratingBar.setEnabled(true);
            binding.ratingBar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
                @Override
                public void onRatingChanged(MaterialRatingBar ratingBar, final float rating) {
                    new CommonDialog.Builder(getContext())
                            .setTitle("提示")
                            .setContent("确认要给评价" + (int) (rating) + "颗星吗?")
                            .build(new CommonDialog.OnDialogClickListener() {
                                @Override
                                public void onCancelClick(View v, Dialog dialog) {

                                }

                                @Override
                                public void onConfirmClick(View v, Dialog dialog) {
                                    evaluateOrder(((int) (rating)));
                                }
                            }).show();
                }
            });
        }
    }

    /**
     * 评价订单
     */
    private void evaluateOrder(final int star) {
        RxHttp.getApi(RepairApi.class)
                .evaluateOrder(listBean.getId(), star)
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(this.<BaseEntity>bindUntilEvent())
                .compose(RxUtils.<BaseEntity>exceptionTransformer())
                .subscribe(new EntityObserver<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        ToastUtils.showToast(entity.getMsg());
                        binding.ratingBar.setProgress(star);
                        binding.ratingBar.setEnabled(false);
                        binding.tvEvaluate.setVisibility(View.GONE);

                        LiveEventBus.get()
                                .with(EventConstan.REFRESH_APPLY)
                                .postValue(null);
                    }
                });
    }

}
