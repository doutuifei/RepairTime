package com.muzi.repairtime.fragment.maintenance;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.applydetail.ApplyDetailActivity;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.adapter.ApplyTakeAdapter;
import com.muzi.repairtime.databinding.FragmentApplyListBinding;
import com.muzi.repairtime.entity.RepairEntity;
import com.muzi.repairtime.event.EventConstan;
import com.muzi.repairtime.event.LiveEventBus;
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
 * 时间: 2019/3/12
 * 邮箱: lipeng@moyi365.com
 * 功能: 维修员申请列表
 */
public class ApplyListFragment extends BaseFragment<FragmentApplyListBinding, BaseViewModel> {

    private int currentPage = 1;
    private int totalPage = 1;
    private ApplyTakeAdapter adapter;
    private List<RepairEntity.PagesBean.ListBean> listBeans = new ArrayList<>();

    public static ApplyListFragment getInstance() {
        ApplyListFragment fragment = new ApplyListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_apply_list;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        super.initView();
        binding.toolbar.setTitle("申请列表");
        initToolbarNav(binding.toolbar);

        binding.refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        binding.recycelView.setLayoutManager(new ExLinearLayoutManger(getContext()));
        binding.recycelView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ApplyDetailActivity.startActivity(getContext(), listBeans.get(position));
            }
        });
        adapter = new ApplyTakeAdapter(R.layout.layout_item_apply_take, listBeans);
        adapter.bindToRecyclerView(binding.recycelView);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        adapter.setEmptyView(R.layout.layout_recyclerview_empty);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (currentPage < totalPage) {
                    currentPage++;
                    getData();
                } else {
                    adapter.loadMoreEnd();
                }
            }
        }, binding.recycelView);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        LiveEventBus.get()
                .with(EventConstan.REFRESH_APPLY, Void.class)
                .observe(this, new Observer<Void>() {
                    @Override
                    public void onChanged(@Nullable Void aVoid) {
                        refresh();
                    }
                });
    }

    private void refresh() {
        if (!listBeans.isEmpty()) {
            currentPage = 1;
            totalPage = 1;
            listBeans.clear();
            adapter.setNewData(listBeans);
        }
        getData();
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
                .repairerOrder(currentPage)
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
                .compose(RxUtils.exceptionTransformer())
                .compose(this.<List<RepairEntity.PagesBean.ListBean>>bindUntilEvent())
                .subscribe(new BaseObserver<List<RepairEntity.PagesBean.ListBean>>() {
                    @Override
                    public void onNext(List<RepairEntity.PagesBean.ListBean> list) {
                        super.onNext(listBeans);
                        listBeans.addAll(list);
                        adapter.notifyDataSetChanged();
                        if (adapter.isLoading()) {
                            adapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        if (adapter.isLoading()) {
                            adapter.loadMoreFail();
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
