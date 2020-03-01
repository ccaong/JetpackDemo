package com.example.myapplication.ui.home;

import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.base.ScrollToTop;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.http.bean.ArticleBean;
import com.example.myapplication.http.bean.home.HomeData;
import com.example.myapplication.ui.activity.web.DetailsActivity;
import com.example.myapplication.ui.adapter.HomeAdapter;
import com.zhouwei.mzbanner.MZBannerView;

import java.util.List;

/**
 * @author devel
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements ScrollToTop {

    HomeAdapter commonAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        initDataChange();
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
        commonAdapter = new HomeAdapter() {
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
                } else if (itemData.getTopArticleList() != null) {

                } else {
                    root.findViewById(R.id.card_view).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DetailsActivity.start(getActivity(), itemData.getArticleList().getLink());
                        }
                    });

                    root.findViewById(R.id.iv_collect).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemData.getArticleList().setCollect(!itemData.getArticleList().isCollect());
                            notifyDataSetChanged();
                            mViewModel.changeArticleCollect(itemData.getArticleList().isCollect(), itemData.getArticleList().getId());
                        }
                    });
                }
            }

            @Override
            public void addTopClickListener(ArticleBean itemData) {
                super.addTopClickListener(itemData);
                DetailsActivity.start(getActivity(), itemData.getLink());

            }

            @Override
            public void addTopCollectListener(ArticleBean itemData) {
                super.addTopCollectListener(itemData);
                mViewModel.changeArticleCollect(itemData.isCollect(), itemData.getId());

            }
        };
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataBinding.recyclerView.setAdapter(commonAdapter);
    }

    private void initDataChange() {
        mViewModel.getHomeList().observe(this, new Observer<List<HomeData>>() {
            @Override
            public void onChanged(List<HomeData> homeDataList) {
                commonAdapter.onItemDatasChanged(homeDataList);
                mDataBinding.mrlRefreshLayout.finishRefreshLoadMore();
                mDataBinding.mrlRefreshLayout.finishRefresh();
            }
        });
    }

    @Override
    public void scrollToTop() {
        mDataBinding.recyclerView.smoothScrollToPosition(0);
    }
}