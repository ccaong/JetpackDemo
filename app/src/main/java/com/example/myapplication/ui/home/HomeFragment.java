package com.example.myapplication.ui.home;

import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.http.bean.home.HomeData;
import com.example.myapplication.ui.activity.web.DetailsActivity;
import com.example.myapplication.ui.adapter.CommonAdapterNew;
import com.zhouwei.mzbanner.MZBannerView;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author devel
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected boolean isSupportLoad() {
        return true;
    }

    @Override
    protected void init() {
        mViewModel.loadHomeData();

        initRecycle();
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mDataBinding.mrlRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {

            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mViewModel.refreshData(true);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                mViewModel.refreshData(false);
            }
        });

    }


    /**
     * 初始化RecycleView
     */
    private void initRecycle() {
        CommonAdapterNew commonAdapter = new CommonAdapterNew(R.layout.item_article, BR.articleBean) {
            @Override
            public void addListener(View root, HomeData itemData, int position) {
                super.addListener(root, itemData, position);
                if (itemData.getBannerData() != null) {
                    MZBannerView banner = root.findViewById(R.id.banner);
                    banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
                        @Override
                        public void onPageClick(View view, int i) {
                            String url = itemData.getBannerData().getBannerData().get(i).getUrl();
                            DetailsActivity.start(getActivity(), url);
                        }
                    });
                } else {
                    root.findViewById(R.id.card_view).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DetailsActivity.start(getActivity(), itemData.getArticleList().getLink());
                        }
                    });
                }
            }
        };
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataBinding.recyclerView.setAdapter(commonAdapter);

        mViewModel.getHomeList().observe(this, new Observer<List<HomeData>>() {
            @Override
            public void onChanged(List<HomeData> homeDataList) {

                commonAdapter.onItemDatasChanged(homeDataList);
                mDataBinding.mrlRefreshLayout.finishRefreshLoadMore();
                mDataBinding.mrlRefreshLayout.finishRefresh();
            }
        });
    }
}