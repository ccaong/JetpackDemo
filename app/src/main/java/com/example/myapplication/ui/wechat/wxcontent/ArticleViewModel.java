package com.example.myapplication.ui.wechat.wxcontent;

import android.text.TextUtils;


import com.example.myapplication.R;
import com.example.myapplication.base.viewmodel.BaseItemViewModel;
import com.example.myapplication.http.bean.ArticleBean;
import com.example.myapplication.navinterface.DetailsNavigator;
import com.example.myapplication.util.CommonUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

/**
 * 文章的ViewModel
 *
 * @author devel
 */
public class ArticleViewModel extends BaseItemViewModel<ArticleBean> {

    public final MutableLiveData<String> tag = new MutableLiveData<>();

    public final MutableLiveData<String> author = new MutableLiveData<>();

    public final MutableLiveData<String> time = new MutableLiveData<>();

    public final MutableLiveData<String> title = new MutableLiveData<>();

    public final MutableLiveData<String> chapterName = new MutableLiveData<>();

    public final MutableLiveData<Boolean> top = new MutableLiveData(false);

    public final MutableLiveData<Boolean> fresh = new MutableLiveData(false);

    private DetailsNavigator mDetailsNavigator;

    public ArticleViewModel() {
    }

    public ArticleViewModel(@NonNull DetailsNavigator detailsNavigator) {
        mDetailsNavigator = detailsNavigator;
    }

    @Override
    protected void setAllModel(@NonNull ArticleBean article) {
        author.postValue(article.getAuthor());
        time.postValue(article.getNiceDate());
        title.postValue(article.getTitle());
        chapterName.postValue(String.format(CommonUtils.getString(R.string.chapter_name_format), article.getSuperChapterName(), article.getChapterName()));

        List<ArticleBean.TagsBean> tagList = article.getTags();
        if (!CommonUtils.isListEmpty(tagList)) {
            tag.postValue(tagList.get(0).getName());
        } else {
            tag.postValue("");
        }

        Boolean top = article.isTop();
        if (top != null) {
            this.top.postValue(top);
        } else {
            this.top.postValue(false);
        }

        Boolean fresh = article.isFresh();
        if (fresh != null) {
            this.fresh.postValue(fresh);
        } else {
            this.fresh.postValue(false);
        }
    }

    public void onClickItem() {
        ArticleBean article = getBaseModel();
        if (article != null && !TextUtils.isEmpty(article.getLink())) {
//            mDetailsNavigator.startDetailsActivity(article.getLink());
        }
    }
}
