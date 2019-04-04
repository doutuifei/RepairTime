package com.muzi.repairtime.fragment.admin;

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
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.adapter.ApplyItemAdapter;
import com.muzi.repairtime.databinding.FragmentStatisticApplyBinding;
import com.muzi.repairtime.entity.FilterEntity;
import com.muzi.repairtime.entity.RepairEntity;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.AdminApi;
import com.muzi.repairtime.manager.ExLinearLayoutManger;
import com.muzi.repairtime.observer.BaseObserver;
import com.muzi.repairtime.widget.CustomLoadMoreView;
import com.muzi.repairtime.widget.dialog.FilterDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 作者: lipeng
 * 时间: 2019/3/25
 * 邮箱: lipeng@moyi365.com
 * 功能: 维修统计
 */
public class StatisticApplyFragment extends BaseFragment<FragmentStatisticApplyBinding, BaseViewModel> {

    public static StatisticApplyFragment getInstance() {
        StatisticApplyFragment fragment = new StatisticApplyFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private FilterEntity filterEntity = new FilterEntity();
    private FilterDialog filterDialog;
    private ApplyItemAdapter adapter;
    private List<RepairEntity.PagesBean.ListBean> listBeans = new ArrayList<>();

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_statistic_apply;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initView() {
        super.initView();
        initToolbarNav(binding.toolbar);
        binding.ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterDialog == null) {
                    filterDialog = new FilterDialog(getActivity()) {
                        @Override
                        public void onFilter(FilterEntity entity) {
                            filterEntity = entity;
                            refresh();
                        }
                    };
                }
                filterDialog.show();
            }
        });
        binding.refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        binding.refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                filterEntity = new FilterEntity();
                refresh();
            }
        });
        binding.recycelView.setLayoutManager(new ExLinearLayoutManger(getContext()));
        adapter = new ApplyItemAdapter(listBeans);
        adapter.bindToRecyclerView(binding.recycelView);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        adapter.setEmptyView(R.layout.layout_recyclerview_empty);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (filterEntity.getCurrentPage() < filterEntity.getTotalPage()) {
                    filterEntity.nextPage();
                    getData();
                } else {
                    adapter.loadMoreEnd();
                }
            }
        }, binding.recycelView);

        binding.recycelView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, final int position) {

            }
        });

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        binding.refreshLayout.setRefreshing(true);
        getData();
    }

    private void refresh() {
        if (!listBeans.isEmpty()) {
            listBeans.clear();
            adapter.setNewData(listBeans);
        }
        getData();
    }

    private void getData() {
        RxHttp.getApi(AdminApi.class)
                .allOrder(filterEntity.getCurrentPage(),
                        filterEntity.getFinishStartTime(),
                        filterEntity.getFinishEndTime(),
                        filterEntity.getReportgroup(),
                        filterEntity.getRepairer(),
                        filterEntity.getOrderstatus(),
                        filterEntity.getRepair_sec(),
                        filterEntity.getPic())
                .doOnNext(new Consumer<RepairEntity>() {
                    @Override
                    public void accept(RepairEntity repairEntity) throws Exception {
                        filterEntity.setCurrentPage(repairEntity.getPages().getCurrentPage());
                        filterEntity.setTotalPage(repairEntity.getPages().getTotalPage());
                    }
                })
                .map(new Function<RepairEntity, List<RepairEntity.PagesBean.ListBean>>() {
                    @Override
                    public List<RepairEntity.PagesBean.ListBean> apply(RepairEntity repairEntity) throws Exception {
                        return repairEntity.getPages().getList();
                    }
                })
                .compose(RxUtils.<List<RepairEntity.PagesBean.ListBean>>scheduling())
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
                    public void onError(String msg) {
                        super.onError(msg);
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
