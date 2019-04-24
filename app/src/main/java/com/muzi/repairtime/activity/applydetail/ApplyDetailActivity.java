package com.muzi.repairtime.activity.applydetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.muzi.repairtime.BR;
import com.muzi.repairtime.BuildConfig;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.adapter.ImageAdapter;
import com.muzi.repairtime.databinding.ActivityApplyDetailBinding;
import com.muzi.repairtime.entity.ImageEntity;
import com.muzi.repairtime.entity.RepairEntity;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.ImageApi;
import com.muzi.repairtime.manager.ExGridLayoutManager;
import com.muzi.repairtime.observer.BaseObserver;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class ApplyDetailActivity extends BaseActivity<ActivityApplyDetailBinding, ApplyDetailViewModel> {

    public static void startActivity(Context context, RepairEntity.PagesBean.ListBean listBean) {
        Intent intent = new Intent(context, ApplyDetailActivity.class);
        intent.putExtra("bean", listBean);
        context.startActivity(intent);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_apply_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
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
    }

    @Override
    public void initData() {
        super.initData();
        RepairEntity.PagesBean.ListBean listBean = getIntent().getExtras().getParcelable("bean");
        getImages(listBean.getId());
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

    private void initRecyclerView(List<String> list) {
        binding.recycelView.setVisibility(View.VISIBLE);
        binding.recycelView.setLayoutManager(new ExGridLayoutManager(this, 3));
        ImageAdapter imageAdapter = new ImageAdapter(R.layout.list_item_image, list);
        binding.recycelView.setAdapter(imageAdapter);
    }

}
