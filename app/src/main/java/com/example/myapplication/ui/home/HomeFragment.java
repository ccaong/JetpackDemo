package com.example.myapplication.ui.home;

import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.entity.ArticleBean;
import com.example.myapplication.entity.ArticleListBean;
import com.example.myapplication.entity.HomeBanner;
import com.example.myapplication.entity.HomeBannerEntity;
import com.example.myapplication.ui.adapter.BannerViewHolder;
import com.example.myapplication.ui.adapter.CommonAdapter;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

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
    protected void init() {
        mViewModel.loadHomeData();
        initRecycle();
        initRefreshLayout();

        mViewModel.getBanner().observe(this, new Observer<HomeBannerEntity>() {
            @Override
            public void onChanged(HomeBannerEntity homeBannerEntity) {
                setBanner(homeBannerEntity.getData());
            }
        });

        mDataBinding.banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                // TODO: 2020/2/20 点击事件
            }
        });
    }

    private void initRefreshLayout() {
        mDataBinding.mrlRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {

            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mViewModel.refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                mViewModel.loadMoreData();
            }
        });

    }


    private void setBanner(List<HomeBanner> movies) {

        mDataBinding.banner.setPages(movies, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        mDataBinding.banner.start();
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycle() {
        CommonAdapter commonAdapter = new CommonAdapter<ArticleBean>(R.layout.item_article, BR.articleBean) {
            @Override
            public void addListener(View root, ArticleBean itemData, int position) {
                super.addListener(root, itemData, position);
            }
        };
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataBinding.recyclerView.setAdapter(commonAdapter);


        mViewModel.getArticleList().observe(this, new Observer<ArticleListBean>() {
            @Override
            public void onChanged(ArticleListBean articleListBean) {

                mDataBinding.mrlRefreshLayout.finishRefresh();
                mDataBinding.mrlRefreshLayout.finishRefreshLoadMore();
                commonAdapter.onItemDatasChanged(articleListBean.getDatas());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDataBinding.banner.pause();
    }

}