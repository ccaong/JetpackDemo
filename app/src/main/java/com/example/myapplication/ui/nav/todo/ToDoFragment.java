package com.example.myapplication.ui.nav.todo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.base.BaseFilterTabBean;
import com.ccj.poptabview.bean.FilterGroup;
import com.ccj.poptabview.bean.FilterTabBean;
import com.ccj.poptabview.listener.OnPopTabSetListener;
import com.ccj.poptabview.loader.PopEntityLoaderImp;
import com.ccj.poptabview.loader.ResultLoaderImp;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.bean.responsebean.ToDoListBean;
import com.example.myapplication.config.Constants;
import com.example.myapplication.databinding.FragmentTodoListBinding;
import com.example.myapplication.navinterface.ScrollToTop;
import com.example.myapplication.ui.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author devel
 */
public class ToDoFragment extends BaseFragment<FragmentTodoListBinding, ToDoViewModel>
        implements ScrollToTop, OnPopTabSetListener<String> {


    private CommonAdapter<ToDoListBean.ToDoData> commonAdapter;

    @Override
    protected boolean isSupportLoad() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_todo_list;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(getActivity()).get(ToDoViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        setHasOptionsMenu(true);

        mViewModel.loadToDoList();

        addMyMethod();

        initRefreshLayout();
        initRecyclerView();
    }

    private void initRefreshLayout() {
        mDataBinding.refreshLayout.setOnRefreshListener(refreshlayout -> mViewModel.refreshData(true));
        mDataBinding.refreshLayout.setOnLoadMoreListener(refreshlayout -> mViewModel.refreshData(false));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            // TODO: 2020/3/4 先判断状态 
            //新增
            NavHostFragment.findNavController(ToDoFragment.this).navigate(R.id.addToDoFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        commonAdapter = new CommonAdapter<ToDoListBean.ToDoData>(R.layout.item_todo_data, com.example.myapplication.BR.toDoData) {
            @Override
            public void addListener(View root, ToDoListBean.ToDoData itemData, int position) {
                super.addListener(root, itemData, position);
                root.findViewById(R.id.card_view).setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.ParamCode.PARAM1, itemData);
                    NavHostFragment.findNavController(ToDoFragment.this).navigate(R.id.toDoContentFragment, bundle);
                });

                root.findViewById(R.id.iv_priority).setOnClickListener(v -> {
                    if (itemData.getPriority() == 0) {
                        itemData.setPriority(1);
                    } else {
                        itemData.setPriority(0);
                    }
                    mViewModel.updateToDoData(itemData);
                    commonAdapter.notifyDataSetChanged();
                });
            }
        };
        mDataBinding.recycle.setAdapter(commonAdapter);
        mDataBinding.recycle.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getToDoListBean().observe(this, toDoListResponse -> {
            if (toDoListResponse.getCurPage() >= toDoListResponse.getPageCount()) {
                mDataBinding.refreshLayout.finishLoadMoreWithNoMoreData();
            }
            mDataBinding.refreshLayout.finishRefresh();
            mDataBinding.refreshLayout.finishLoadMore();
            commonAdapter.onItemDatasChanged(toDoListResponse.getDatas());
        });
    }

    /**
     * 筛选条件
     */
    private void addMyMethod() {
        FilterGroup filterGroup1 = getStatusData("状态");
        FilterGroup filterGroup3 = getPriorityData("优先级");

        mDataBinding.popView.setOnPopTabSetListener(this)
                .setPopEntityLoader(new PopEntityLoaderImp()).setResultLoader(new ResultLoaderImp())
                .addFilterItem(filterGroup1.getTab_group_name(), filterGroup1.getFilter_tab(), filterGroup1.getTab_group_type(), filterGroup1.getSingle_or_mutiply())
                .addFilterItem(filterGroup3.getTab_group_name(), filterGroup3.getFilter_tab(), filterGroup3.getTab_group_type(), filterGroup3.getSingle_or_mutiply());
    }

    private FilterGroup getStatusData(String groupName) {

        FilterGroup filterGroup = new FilterGroup();

        filterGroup.setTab_group_name(groupName);
        filterGroup.setTab_group_type(FilterConfig.TYPE_POPWINDOW_SINGLE);
        filterGroup.setSingle_or_mutiply(FilterConfig.FILTER_TYPE_SINGLE);

        List<BaseFilterTabBean> singleFilterList = new ArrayList<>();
        for (int i = -1; i < 4; i++) {
            //一级filter
            FilterTabBean singleFilterBean = new FilterTabBean();
            singleFilterBean.setTab_id(String.valueOf(i));
            switch (i) {
                case -1:
                    singleFilterBean.setTab_name("全部");
                    break;
                case 0:
                    singleFilterBean.setTab_name("学习");
                    break;
                case 1:
                    singleFilterBean.setTab_name("工作");
                    break;
                case 2:
                    singleFilterBean.setTab_name("生活");
                    break;
                case 3:
                    singleFilterBean.setTab_name("其他");
                    break;
                default:
                    break;
            }
            singleFilterList.add(singleFilterBean);
        }

        filterGroup.setFilter_tab(singleFilterList);
        return filterGroup;

    }

    private FilterGroup getPriorityData(String groupName) {

        FilterGroup filterGroup = new FilterGroup();

        filterGroup.setTab_group_name(groupName);
        filterGroup.setTab_group_type(FilterConfig.TYPE_POPWINDOW_SINGLE);
        filterGroup.setSingle_or_mutiply(FilterConfig.FILTER_TYPE_SINGLE);

        List<BaseFilterTabBean> singleFilterList = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            FilterTabBean singleFilterBean = new FilterTabBean();
            singleFilterBean.setTab_id(String.valueOf(i));
            switch (i) {
                case -1:
                    singleFilterBean.setTab_name("全部");
                    break;
                case 0:
                    singleFilterBean.setTab_name("重要");
                    break;
                case 1:
                    singleFilterBean.setTab_name("一般");
                    break;
                default:
                    break;
            }
            singleFilterList.add(singleFilterBean);
        }
        filterGroup.setFilter_tab(singleFilterList);
        return filterGroup;
    }

    /**
     * 选择筛选条件
     *
     * @param index
     * @param lable
     * @param params
     * @param value
     */
    @Override
    public void onPopTabSet(int index, String lable, String params, String value) {

        if (value != null) {
            if (index == 0) {
                if (value.equals("全部")) {
                    mViewModel.setQueryType(0);
                } else if (value.equals("学习")) {
                    mViewModel.setQueryType(1);
                } else if (value.equals("工作")) {
                    mViewModel.setQueryType(2);
                } else if (value.equals("生活")) {
                    mViewModel.setQueryType(3);
                } else if (value.equals("其他")) {
                    mViewModel.setQueryType(4);
                }
            } else {
                if (value.equals("全部")) {
                    mViewModel.setQueryPriority(0);
                } else if (value.equals("重要")) {
                    mViewModel.setQueryPriority(1);
                } else if (value.equals("一般")) {
                    mViewModel.setQueryPriority(2);
                }
            }
        }
    }

    @Override
    public void scrollToTop() {
        mDataBinding.recycle.smoothScrollToPosition(0);
    }
}
