package com.muzi.repairtime.activity.applydetail;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.muzi.repairtime.BR;
import com.muzi.repairtime.BuildConfig;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.ImagePreviewActivity;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.adapter.ImageAdapter;
import com.muzi.repairtime.databinding.ActivityApplyItemDetailBinding;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.entity.ImageEntity;
import com.muzi.repairtime.entity.RepairEntity;
import com.muzi.repairtime.event.EventConstan;
import com.muzi.repairtime.event.LiveEventBus;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.ImageApi;
import com.muzi.repairtime.http.api.RepairApi;
import com.muzi.repairtime.manager.ExGridLayoutManager;
import com.muzi.repairtime.observer.BaseObserver;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.utils.ToastUtils;
import com.muzi.repairtime.widget.dialog.CommonDialog;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * 普通用户 我的申请 订单详情
 */
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
        getImages(listBean.getId());
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

    /**
     * 加载图片
     *
     * @param id
     */
    private void getImages(int id) {
        RxHttp.getApi(ImageApi.class)
                .getImage(id)
                .flatMap(new Function<ImageEntity, ObservableSource<ImageEntity.PagesBean>>() {
                    @Override
                    public ObservableSource<ImageEntity.PagesBean> apply(ImageEntity imageEntity) throws Exception {
                        return Observable.fromIterable(imageEntity.getPages());
                    }
                })
                .map(new Function<ImageEntity.PagesBean, String>() {
                    @Override
                    public String apply(ImageEntity.PagesBean pagesBean) throws Exception {
                        return BuildConfig.IMAGE_URL + pagesBean.getVirtualPath();
                    }
                })
                .toList()
                .toObservable()
                .compose(RxUtils.<List<String>>scheduling())
                .compose(this.<List<String>>bindUntilEvent())
                .subscribe(new BaseObserver<List<String>>() {
                    @Override
                    public void onNext(List<String> list) {
                        super.onNext(list);
                        initRecyclerView(list);
                    }
                });
    }

    private void initRecyclerView(final List<String> list) {
        binding.recycelView.setVisibility(View.VISIBLE);
        binding.recycelView.setLayoutManager(new ExGridLayoutManager(this, 4));
        ImageAdapter imageAdapter = new ImageAdapter(R.layout.list_item_image, list);
        imageAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return 1;
            }
        });
        binding.recycelView.setAdapter(imageAdapter);
        binding.recycelView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImagePreviewActivity.startIntent(getContext(),list,position);
            }
        });
    }

}
