package com.muzi.repairtime.fragment.apply;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.muzi.repairtime.BR;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.adapter.ApplyItemAdapter;
import com.muzi.repairtime.databinding.FragmentItemApplyBinding;
import com.muzi.repairtime.entity.RepairEntity;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.RepairApi;
import com.muzi.repairtime.manager.ExLinearLayoutManger;
import com.muzi.repairtime.observer.BaseObserver;
import com.muzi.repairtime.widget.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ApplyItemFragment extends BaseFragment<FragmentItemApplyBinding, ApplyItemViewModel> {

    private String status;
    private int currentPage = 1;
    private int totalPage = 1;
    private ApplyItemAdapter applyItemAdapter;
    private List<RepairEntity.PagesBean.ListBean> listBeans = new ArrayList<>();

    public static ApplyItemFragment getInstance(String status) {
        ApplyItemFragment fragment = new ApplyItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_item_apply;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam(Bundle bundle) {
        super.initParam(bundle);
        status = bundle.getString("status");
    }

    @Override
    public void initView() {
        super.initView();
        binding.refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!listBeans.isEmpty()) {
                    currentPage = 1;
                    totalPage = 1;
                    listBeans.clear();
                    applyItemAdapter.setNewData(listBeans);
                }
                getData();
            }
        });
        binding.recycelView.setLayoutManager(new ExLinearLayoutManger(getContext()));
        applyItemAdapter = new ApplyItemAdapter(R.layout.layout_item_apply, listBeans);
        applyItemAdapter.bindToRecyclerView(binding.recycelView);
        applyItemAdapter.setLoadMoreView(new CustomLoadMoreView());
        applyItemAdapter.setEmptyView(R.layout.layout_recyclerview_empty);
        applyItemAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (currentPage < totalPage) {
                    currentPage++;
                    getData();
                } else {
                    applyItemAdapter.loadMoreEnd();
                }
            }
        }, binding.recycelView);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        binding.refreshLayout.setRefreshing(true);
        getData();
    }

    /**
     * 获取信息
     */
    private void getData() {
        RxHttp.getApi(RepairApi.class)
                .getMyRepair(currentPage, status)
                .doOnNext(new Consumer<RepairEntity>() {
                    @Override
                    public void accept(RepairEntity repairEntity) throws Exception {
                        totalPage = repairEntity.getPages().getTotalPage();
                        currentPage = repairEntity.getPages().getCurrentPage();
                    }
                })
                .map(new Function<RepairEntity, List<RepairEntity.PagesBean.ListBean>>() {
                    @Override
                    public List<RepairEntity.PagesBean.ListBean> apply(RepairEntity repairEntity) throws Exception {
                        return repairEntity.getPages().getList();
                    }
                })
                .compose(RxUtils.<List<RepairEntity.PagesBean.ListBean>>scheduling())
                .subscribe(new BaseObserver<List<RepairEntity.PagesBean.ListBean>>() {
                    @Override
                    public void onNext(List<RepairEntity.PagesBean.ListBean> list) {
                        super.onNext(listBeans);
                        listBeans.addAll(list);
                        applyItemAdapter.notifyDataSetChanged();
                        if (applyItemAdapter.isLoading()) {
                            applyItemAdapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);
                        if (applyItemAdapter.isLoading()) {
                            applyItemAdapter.loadMoreFail();
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        if (binding.refreshLayout.isRefreshing()) {
                            binding.refreshLayout.setRefreshing(false);
                        }
                    }
                });
    }

}
