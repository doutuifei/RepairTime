package com.muzi.repairtime.fragment.admin;

import android.app.Dialog;
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
import com.muzi.repairtime.activity.CreateNoticeActivity;
import com.muzi.repairtime.activity.base.BaseFragment;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.activity.detail.NoticeDetailActivity;
import com.muzi.repairtime.adapter.NoticeAdminAdapter;
import com.muzi.repairtime.databinding.FragmentPubNoticeBinding;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.entity.NoticeEntity;
import com.muzi.repairtime.event.EventConstan;
import com.muzi.repairtime.event.LiveEventBus;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.AdminApi;
import com.muzi.repairtime.http.api.NoticeApi;
import com.muzi.repairtime.interfaces.onSwipeListener;
import com.muzi.repairtime.manager.ExLinearLayoutManger;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.widget.CustomLoadMoreView;
import com.muzi.repairtime.widget.dialog.CommonDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: lipeng
 * 时间: 2019/3/13
 * 邮箱: lipeng@moyi365.com
 * 功能: 公告发布
 */
public class PubNoticeFragment extends BaseFragment<FragmentPubNoticeBinding, BaseViewModel> {

    private int currentPage = 1;
    private int totalPage = 1;
    private NoticeAdminAdapter adapter;
    private List<NoticeEntity.PagesBean.ListBean> listBeans = new ArrayList<>();
    private CommonDialog deleteDialog;

    public static PubNoticeFragment getInstance() {
        PubNoticeFragment fragment = new PubNoticeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_pub_notice;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        LiveEventBus.get()
                .with(EventConstan.REFRESH_NOTICE, Object.class)
                .observe(this, new Observer<Object>() {
                    @Override
                    public void onChanged(@Nullable Object o) {
                        refresh();
                    }
                });
    }

    @Override
    public void initView() {
        super.initView();
        binding.toolbar.setTitle("公告发布");
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
        adapter = new NoticeAdminAdapter(R.layout.layout_item_admin_notice, listBeans);
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

        initListener();
    }

    private void initListener() {
        //新建公告
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNoticeActivity.startActivity(getContext());
            }
        });

        //查看详情
        binding.recycelView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                NoticeEntity.PagesBean.ListBean listBean = listBeans.get(position);
                NoticeDetailActivity.startActivity(getContext(), listBean.getTitle(), listBean.getContent());
            }
        });

        //删除公告
        adapter.setOnDelListener(new onSwipeListener() {
            @Override
            public void onDelete(int position) {
                deleteNoticeDialog(position);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        binding.refreshLayout.setRefreshing(true);
        getData();
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        if (!listBeans.isEmpty()) {
            currentPage = 1;
            totalPage = 1;
            listBeans.clear();
            adapter.setNewData(listBeans);
        }
        getData();
    }

    /**
     * 获取公告信息
     */
    private void getData() {
        RxHttp.getApi(NoticeApi.class)
                .getAnnouncements(currentPage)
                .compose(RxUtils.<NoticeEntity>scheduling())
                .compose(RxUtils.<NoticeEntity>exceptionTransformer())
                .compose(this.<NoticeEntity>bindUntilEvent())
                .subscribe(new EntityObserver<NoticeEntity>(this) {

                    @Override
                    public void onSuccess(NoticeEntity noticeEntity) {
                        totalPage = noticeEntity.getPages().getTotalPage();
                        currentPage = noticeEntity.getPages().getCurrentPage();
                        listBeans.addAll(noticeEntity.getPages().getList());
                        adapter.notifyDataSetChanged();
                        if (adapter.isLoading()) {
                            adapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
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

    /**
     * 删除公告确认弹窗
     *
     * @param position
     */
    private void deleteNoticeDialog(final int position) {
        if (deleteDialog == null) {
            deleteDialog = new CommonDialog.Builder(getContext())
                    .setTitle("提示")
                    .setContent("确定要删除这条公告吗？")
                    .build(new CommonDialog.OnDialogClickListener() {
                        @Override
                        public void onCancelClick(View v, Dialog dialog) {

                        }

                        @Override
                        public void onConfirmClick(View v, Dialog dialog) {
                            delNotice(position);
                        }
                    });
        }
        deleteDialog.show();
    }

    /**
     * 删除公告
     *
     * @param position
     */
    private void delNotice(final int position) {
        RxHttp.getApi(AdminApi.class)
                .delNotice(listBeans.get(position).getId())
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(RxUtils.<BaseEntity>exceptionTransformer())
                .compose(this.<BaseEntity>bindUntilEvent())
                .subscribe(new EntityObserver<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        listBeans.remove(position);
                        adapter.notifyItemRemoved(position);
                    }
                });
    }

}
