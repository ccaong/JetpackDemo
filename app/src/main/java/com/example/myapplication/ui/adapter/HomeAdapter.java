package com.example.myapplication.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.bean.responsebean.ArticleBean;
import com.example.myapplication.bean.responsebean.home.HomeData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static com.example.myapplication.config.App.getContext;

/**
 * 主页的Adapter
 *
 * @author devel
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HomeData> mList;

    public HomeAdapter() {
    }

    /**
     * 添加监听回调
     *
     * @param root
     * @param itemData
     * @param position
     */
    public void addListener(View root, HomeData itemData, int position) {
    }

    /**
     * 添加监听回调
     *
     * @param itemData
     */
    public void addTopClickListener(ArticleBean itemData) {
    }
    /**
     * 添加监听回调
     *
     * @param itemData
     */
    public void addTopCollectListener(ArticleBean itemData) {
    }



    /**
     * 改变数据
     *
     * @param newItemDatas
     */
    public void onItemDatasChanged(List<HomeData> newItemDatas) {
        this.mList = newItemDatas;
        notifyDataSetChanged();
    }

    public int getItemLayout(HomeData itemData) {
        if (itemData.getBannerData() != null) {
            return R.layout.item_home_banner;
        } else if (itemData.getTopArticleList() != null) {
            return R.layout.item_home_top;
        } else {
            return R.layout.item_article;
        }
    }


    @Override
    public int getItemViewType(int position) {
        return getItemLayout(mList.get(position));
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), viewType, viewGroup, false);
        return new CommonViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder commonViewHolder, int position) {
        HomeData homeData = mList.get(position);

        if (homeData.getArticleList() != null) {
            //绑定数据
            ((CommonViewHolder) commonViewHolder).binding.setVariable(BR.articleBean, homeData.getArticleList());
        } else if (homeData.getTopArticleList() != null){
            ((CommonViewHolder) commonViewHolder).binding.setVariable(BR.topArticle, homeData.getTopArticleList());
            showTopArticle(((CommonViewHolder) commonViewHolder).binding.getRoot().findViewById(R.id.recycler_view),homeData.getTopArticleList());
        }else {
            ((CommonViewHolder) commonViewHolder).binding.setVariable(BR.bannerData, homeData.getBannerData());
        }
        addListener(((CommonViewHolder) commonViewHolder).binding.getRoot(), mList.get(position), position);
        //防止数据闪烁
        ((CommonViewHolder) commonViewHolder).binding.executePendingBindings();
    }

    private void showTopArticle(RecyclerView recyclerView, HomeData.TopArticle topArticle){

        CommonAdapter commonAdapter = new CommonAdapter<ArticleBean>(topArticle.getArticleBeanList(),R.layout.item_article, BR.articleBean) {
            @Override
            public void addListener(View root, ArticleBean itemData, int position) {
                super.addListener(root, itemData, position);
                root.findViewById(R.id.card_view).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addTopClickListener(itemData);
                    }
                });

                root.findViewById(R.id.iv_collect).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemData.setCollect(!itemData.isCollect());
                        notifyDataSetChanged();
                        addTopCollectListener(itemData);
//                        mViewModel.changeArticleCollect(itemData.isCollect(), itemData.getId());
                    }
                });
            }
        };
        recyclerView.setAdapter(commonAdapter);

        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
}
