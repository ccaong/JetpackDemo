package com.example.myapplication.ui.wechat.wxcontent;

import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseViewHolder;
import com.example.myapplication.databinding.ItemArticleNewBinding;
import com.example.myapplication.navinterface.DetailsNavigator;
import com.example.myapplication.ui.activity.web.DetailsActivity;

import androidx.annotation.NonNull;


/**
 * 文章的ViewHolder
 *
 * @author devel
 */
public class ArticleViewHolder extends BaseViewHolder<ItemArticleNewBinding, ArticleViewModel>
        implements DetailsNavigator {

    public ArticleViewHolder(@NonNull ViewGroup parent) {
        super(parent, R.layout.item_article_new);
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ArticleViewModel(this);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        mDataBinding.tvTop.setVisibility(View.GONE);
        mDataBinding.tvFresh.setVisibility(View.GONE);
        mDataBinding.tvTag.setVisibility(View.GONE);
    }

    @Override
    public void startDetailsActivity(String url) {
        DetailsActivity.start(itemView.getContext(), url);
    }
}
