package com.muzi.repairtime.activity.applydetail;

import android.app.Application;
import android.app.Dialog;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.command.BindingCommand;
import com.muzi.repairtime.command.BindingConsumerAction;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.entity.RepairEntity;
import com.muzi.repairtime.event.EventConstan;
import com.muzi.repairtime.event.LiveEventBus;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.RepairApi;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.utils.DateUtils;
import com.muzi.repairtime.utils.ToastUtils;
import com.muzi.repairtime.widget.dialog.CommonDialog;

/**
 * 作者: lipeng
 * 时间: 2019/4/3
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ApplyItemDetailViewModel extends BaseViewModel {

    private RepairEntity.PagesBean.ListBean listBean;

    /**
     * 报修科室
     */
    public ObservableField<String> room = new ObservableField<>("");

    /**
     * 负责人
     */
    public ObservableField<String> principal = new ObservableField<>("");

    /**
     * 报修人
     */
    public ObservableField<String> repoter = new ObservableField<>("");

    /**
     * 维修项目
     */
    public ObservableField<String> repair = new ObservableField<>("");

    /**
     * 报修时间
     */
    public ObservableField<String> time = new ObservableField<>("");

    /**
     * 问题描述
     */
    public ObservableField<String> desc = new ObservableField<>("");

    /**
     * 满意度
     */
    public ObservableInt evaluate = new ObservableInt();

    /**
     * 状态
     */
    public ObservableField<String> status = new ObservableField<>("");

    /**
     * 是否接单
     */
    public ObservableInt statusId = new ObservableInt();

    /**
     * 删除
     */
    public BindingCommand<View> delete = new BindingCommand<>(new BindingConsumerAction<View>() {
        @Override
        public void call(View view) {
            new CommonDialog.Builder(getContext())
                    .setTitle("提示")
                    .setContent("确认删除这个单子吗?")
                    .build(new CommonDialog.OnDialogClickListener() {
                        @Override
                        public void onCancelClick(View v, Dialog dialog) {

                        }

                        @Override
                        public void onConfirmClick(View v, Dialog dialog) {
                            deleteOrder();
                        }
                    }).show();
        }
    });

    public BindingCommand<View> complete = new BindingCommand<>(new BindingConsumerAction<View>() {
        @Override
        public void call(View view) {
            new CommonDialog.Builder(getContext())
                    .setTitle("提示")
                    .setContent("确认这个单子完成了吗?")
                    .build(new CommonDialog.OnDialogClickListener() {
                        @Override
                        public void onCancelClick(View v, Dialog dialog) {

                        }

                        @Override
                        public void onConfirmClick(View v, Dialog dialog) {
                            finishOrder(true);
                        }
                    }).show();
        }
    });

    public BindingCommand<View> uncomplete = new BindingCommand<>(new BindingConsumerAction<View>() {
        @Override
        public void call(View view) {
            new CommonDialog.Builder(getContext())
                    .setTitle("提示")
                    .setContent("确认这个单子未完成吗?")
                    .build(new CommonDialog.OnDialogClickListener() {
                        @Override
                        public void onCancelClick(View v, Dialog dialog) {

                        }

                        @Override
                        public void onConfirmClick(View v, Dialog dialog) {
                            finishOrder(false);
                        }
                    }).show();
        }
    });

    public ApplyItemDetailViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void initParam(Bundle bundle) {
        super.initParam(bundle);
        listBean = bundle.getParcelable("bean");
        room.set(listBean.getReportgroup());
        principal.set(listBean.getPic());
        repoter.set(listBean.getReporter());
        repair.set(listBean.getRepair_sec());
        time.set(DateUtils.long2String(listBean.getReporttime()));
        desc.set(listBean.getProblem());
        evaluate.set(listBean.getCs_id());
        status.set(listBean.getOrderstatus());
        statusId.set(listBean.getStatus_id());
    }

    /**
     * 删除订单
     */
    private void deleteOrder() {
        RxHttp.getApi(RepairApi.class)
                .deleteOrder(listBean.getId())
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(RxUtils.<BaseEntity>bindToLifecycle(getLifecycleProvider()))
                .subscribe(new EntityObserver<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        ToastUtils.showToast(entity.getMsg());
                        LiveEventBus.get()
                                .with(EventConstan.REFRESH_APPLY)
                                .postValue(null);
                        finish();
                    }
                });
    }

    /**
     * 更改订单状态
     *
     * @param complete
     */
    private void finishOrder(final boolean complete) {
        RxHttp.getApi(RepairApi.class)
                .finishOrder(listBean.getId(), complete)
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(RxUtils.<BaseEntity>bindToLifecycle(getLifecycleProvider()))
                .subscribe(new EntityObserver<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        ToastUtils.showToast(entity.getMsg());
                        status.set(complete ? "已完成" : "未完成");
                        statusId.set(0);
                        LiveEventBus.get()
                                .with(EventConstan.REFRESH_APPLY)
                                .postValue(null);
                    }
                });
    }

}